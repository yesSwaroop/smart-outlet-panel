package com.grievingcat.spanel.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.grievingcat.spanel.R
import com.grievingcat.spanel.Socket1Activity
import com.grievingcat.spanel.Socket2Activity
import com.grievingcat.spanel.Socket3Activity
import com.grievingcat.spanel.databinding.FragmentPowerBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PowerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PowerFragment : Fragment() {

    private lateinit var binding : FragmentPowerBinding
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_power, container, false)
        // For view binding in fragments use this
        binding = FragmentPowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = FirebaseDatabase.getInstance("https://spanel-fe94c-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference()
        var timed = false
        var time : String = ""
        var actTime : String = ""
        val socket = getSocket()
        val bl = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                when(snapshot.value.toString().toBoolean()){
                    true -> binding.connectButton.text = "DISCONNECT"
                    else -> binding.connectButton.text = "CONNECT"
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.child(socket).child("active").addValueEventListener(bl)
        var connected = false
        database.child(socket).child("active").get().addOnSuccessListener {
            connected = it.value.toString().toBoolean()
            if(connected){
                binding.connectButton.text = "DISCONNECTED"
            }
        }
        binding.scroll1.maxValue = 24
        binding.scroll2.maxValue = 59
        binding.scroll3.maxValue = 59
        binding.timedConnection.setOnCheckedChangeListener { _, checked ->
            timed = false
            if(checked)
                timed = true
        }
        binding.scroll1.viewTreeObserver.addOnScrollChangedListener {
            var hh = 0 ;var mm = 0 ;var ss = 0
            hh = binding.scroll1.value.toInt()
            mm = binding.scroll2.value.toInt()
            ss = binding.scroll3.value.toInt()
            time += if(hh/10==0) "0$hh" else "$hh"
            time += if (mm/10 == 0) ":0$mm" else ":$mm"
            time += if (ss/10 == 0) ":0$ss" else ":$ss"
            binding.timer.text = time
            actTime = time
            time=""
        }
        binding.connectButton.setOnClickListener {
            if(!connected){
                if(timed){
                    database.child(socket).child("timed").setValue(true)
                    database.child(socket).child("timer").setValue(actTime)
                }
                database.child(socket).child("active").setValue(true)
                binding.connectButton.text = "DISCONNECT"
                connected = true
            }else{
                database.child(socket).child("active").setValue(false)
                binding.connectButton.text = "CONNECT"
                connected = false
                database.child(socket).child("timed").setValue(false)
                database.child(socket).child("timer").setValue("00:00:00")
            }
        }
    }

    private fun getSocket() : String{
        return when(activity){
            is Socket1Activity -> "socket1"
            is Socket2Activity -> "socket2"
            is Socket3Activity -> "socket3"
            else -> "socket4"
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PowerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PowerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}