package com.grievingcat.spanel.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.grievingcat.spanel.R
import com.grievingcat.spanel.Socket1Activity
import com.grievingcat.spanel.Socket2Activity
import com.grievingcat.spanel.Socket3Activity
import com.grievingcat.spanel.databinding.FragmentConsumptionBinding
import com.grievingcat.spanel.databinding.FragmentPowerBinding
import kotlinx.coroutines.*
import java.lang.Integer.max
import java.util.concurrent.TimeUnit
import kotlin.math.min

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ConsumptionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConsumptionFragment : Fragment() {

    private lateinit var binding : FragmentConsumptionBinding
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
        binding = FragmentConsumptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val socket = getSocket()
        val database = FirebaseDatabase.getInstance("https://spanel-fe94c-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

        binding.updcons?.setOnClickListener {
            Toast.makeText(activity,"Please wait, fetching data...",Toast.LENGTH_SHORT).show()
            database.child(socket).child("send_data").setValue("true")
            CoroutineScope(Dispatchers.IO).launch {
                delay(TimeUnit.SECONDS.toMillis(5))
                withContext(Dispatchers.Main) {
                    Toast.makeText(activity,"Data fetch successful!!",Toast.LENGTH_LONG).show()
                    database.child(socket).child("powerd").get().addOnSuccessListener {it->
                        var powd = it.value.toString()
                        while(powd.length<8){
                            powd = "0$powd"
                        }
                        binding.consumpd?.text = powd.substring(0,8)
                        database.child(socket).child("powerm").get().addOnSuccessListener {it->
                            var powm = it.value.toString()
                            while(powm.length<8){
                                powm = "0$powm"
                            }
                            binding.consumpm?.text  = powm.substring(0, 8)
                            database.child(socket).child("send_data").setValue("false")
                        }
                    }
                }
            }
        }
        binding.updconn?.setOnClickListener {
            database.child(socket).child("connectionsd").get().addOnSuccessListener {it->
                binding.connecd?.text = it.value.toString()
            }
            database.child(socket).child("connectionsm").get().addOnSuccessListener {it->
                binding.connecm?.text = it.value.toString()
            }
        }
        binding.calccost.setOnClickListener {
            val rate = binding.cost?.text.toString().toDouble()
            database.child(socket).child("powerd").get().addOnSuccessListener {it->
                val costday = (it.value.toString().toDouble()*rate).toString()
                binding.costd?.text = costday.substring(0,min(8,costday.length))
            }
            database.child(socket).child("powerm").get().addOnSuccessListener {it->
                val costmonth = (it.value.toString().toDouble()*rate).toString()
                binding.costm?.text = costmonth.substring(0,min(8,costmonth.length))
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ConsumptionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConsumptionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
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
}