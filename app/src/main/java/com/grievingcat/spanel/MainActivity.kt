package com.grievingcat.spanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.grievingcat.spanel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var time1 : String
    lateinit var time2 : String
    lateinit var time3 : String
    lateinit var time4 : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Waiting screen, shown while app is connecting to database and updating UI
        setContentView(R.layout.waiting_screen)
        // Connecting to database
        val database = FirebaseDatabase.getInstance("https://spanel-fe94c-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference()
        val binding = ActivityMainBinding.inflate(layoutInflater);

        // Timers
        var timer1 : CountDownTimer
        var timer2 : CountDownTimer
        var timer3 : CountDownTimer
        var timer4 : CountDownTimer

        // Value event listeners for each socket image
        val s1l = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                when(snapshot.value.toString().toBoolean()){
                    true -> binding.socket1.setImageResource(R.drawable.ic_plugged_short)
                    else -> binding.socket1.setImageResource(R.drawable.ic_unplugged)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        val s2l = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                when(snapshot.value.toString().toBoolean()){
                    true -> binding.socket2.setImageResource(R.drawable.ic_plugged_short)
                    else -> binding.socket2.setImageResource(R.drawable.ic_unplugged)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        val s3l = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                when(snapshot.value.toString().toBoolean()){
                    true -> binding.socket3.setImageResource(R.drawable.ic_plugged_short)
                    else -> binding.socket3.setImageResource(R.drawable.ic_unplugged)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        val s4l = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                when(snapshot.value.toString().toBoolean()){
                    true -> binding.socket4.setImageResource(R.drawable.ic_plugged_short)
                    else -> binding.socket4.setImageResource(R.drawable.ic_unplugged)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }

        // Activating intent mechanism
        intoInfo(binding.socket1)
        intoInfo(binding.socket2)
        intoInfo(binding.socket3)
        intoInfo(binding.socket4)

        // Beginning interaction with database
        database.child("test").get().addOnSuccessListener { it ->
            val msg = it.value.toString()
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            for(i in 1..4){
                database.child("socket$i").child("active").get().addOnSuccessListener {
                    if(it.value.toString().toBoolean()){
                        when(i){
                            1 -> binding.socket1.setImageResource(R.drawable.ic_plugged_short)
                            2 -> binding.socket2.setImageResource(R.drawable.ic_plugged_short)
                            3 -> binding.socket3.setImageResource(R.drawable.ic_plugged_short)
                            else -> binding.socket4.setImageResource(R.drawable.ic_plugged_short)
                        }
                    }
                    else{
                        when(i){
                            1 -> binding.socket1.setImageResource(R.drawable.ic_unplugged)
                            2 -> binding.socket2.setImageResource(R.drawable.ic_unplugged)
                            3 -> binding.socket3.setImageResource(R.drawable.ic_unplugged)
                            else -> binding.socket4.setImageResource(R.drawable.ic_unplugged)
                        }
                    }
                }
            }
            database.child("socket1").child("active").addValueEventListener(s1l)
            database.child("socket2").child("active").addValueEventListener(s2l)
            database.child("socket3").child("active").addValueEventListener(s3l)
            database.child("socket4").child("active").addValueEventListener(s4l)
//            database.child("socket1").child("timer").get().addOnSuccessListener { it->
//                val timeLeft = it.value.toString()
//                binding.mainTimer1.text = timeLeft
//                val timeLeftSecs : Long = (timeLeft[0].code.toLong()*10+timeLeft[1].code-48*11)*3600 +
//                                     (timeLeft[3].code*10+timeLeft[4].code-48*11)*60 +
//                                     (timeLeft[6].code*10+timeLeft[7].code-48*11) + 1
//                timer1 = object : CountDownTimer(timeLeftSecs*1000,1000) {
//                    override fun onTick(millisUntilFinished: Long) {
//                        var secsLeft = millisUntilFinished/1000
//                        val hh = secsLeft/(3600)
//                        secsLeft -= hh*3600
//                        val mm = secsLeft/60
//                        secsLeft -= mm*60
//                        val ss = secsLeft
//                        time1 = if(hh.toInt()/10==0) "0$hh" else "$hh"
//                        time1 += if (mm.toInt() / 10 == 0) ":0$mm" else ":$mm"
//                        time1 += if (ss.toInt() / 10 == 0) ":0$ss" else ":$ss"
//                        binding.mainTimer1.text = time1
//                    }
//                    override fun onFinish() {
//
//                    }
//                }.start()
//            }
//            database.child("socket2").child("timer").get().addOnSuccessListener { it->
//                binding.mainTimer2.text = it.value.toString()
//                val timeLeft = it.value.toString()
//                binding.mainTimer2.text = timeLeft
//                val timeLeftSecs : Long = (timeLeft[0].code.toLong()*10+timeLeft[1].code-48*11)*3600 +
//                        (timeLeft[3].code*10+timeLeft[4].code-48*11)*60 +
//                        (timeLeft[6].code*10+timeLeft[7].code-48*11) + 1
//                timer2 = object : CountDownTimer(timeLeftSecs*1000,1000) {
//                    override fun onTick(millisUntilFinished: Long) {
//                        var secsLeft = millisUntilFinished/1000
//                        val hh = secsLeft/(3600)
//                        secsLeft -= hh*3600
//                        val mm = secsLeft/60
//                        secsLeft -= mm*60
//                        val ss = secsLeft
//                        time2 = if(hh.toInt()/10==0) "0$hh" else "$hh"
//                        time2 += if (mm.toInt() / 10 == 0) ":0$mm" else ":$mm"
//                        time2 += if (ss.toInt() / 10 == 0) ":0$ss" else ":$ss"
//                        binding.mainTimer2.text = time2
//                    }
//                    override fun onFinish() {
//
//                    }
//                }.start()
//            }
//            database.child("socket3").child("timer").get().addOnSuccessListener { it->
//                binding.mainTimer3.text = it.value.toString()
//                val timeLeft4 = it.value.toString()
//                binding.mainTimer3.text = timeLeft4
//                val timeLeftSecs : Long = (timeLeft4[0].code.toLong()*10+timeLeft4[1].code-48*11)*3600 +
//                        (timeLeft4[3].code*10+timeLeft4[4].code-48*11)*60 +
//                        (timeLeft4[6].code*10+timeLeft4[7].code-48*11) + 1
//                timer3 = object : CountDownTimer(timeLeftSecs*1000,1000) {
//                    override fun onTick(millisUntilFinished: Long) {
//                        var secsLeft = millisUntilFinished/1000
//                        val hh = secsLeft/(3600)
//                        secsLeft -= hh*3600
//                        val mm = secsLeft/60
//                        secsLeft -= mm*60
//                        val ss = secsLeft
//                        time3 = if(hh.toInt()/10==0) "0$hh" else "$hh"
//                        time3 += if (mm.toInt() / 10 == 0) ":0$mm" else ":$mm"
//                        time3 += if (ss.toInt() / 10 == 0) ":0$ss" else ":$ss"
//                        binding.mainTimer3.text = time3
//                    }
//                    override fun onFinish() {
//
//                    }
//                }.start()
//            }
//            database.child("socket4").child("timer").get().addOnSuccessListener { it->
//                binding.mainTimer4.text = it.value.toString()
//                val timeLeft = it.value.toString()
//                binding.mainTimer4.text = timeLeft
//                val timeLeftSecs : Long = (timeLeft[0].code.toLong()*10+timeLeft[1].code-48*11)*3600 +
//                        (timeLeft[3].code*10+timeLeft[4].code-48*11)*60 +
//                        (timeLeft[6].code*10+timeLeft[7].code-48*11) + 1
//                timer4 = object : CountDownTimer(timeLeftSecs*1000,1000) {
//                    override fun onTick(millisUntilFinished: Long) {
//                        var secsLeft = millisUntilFinished/1000
//                        val hh = secsLeft/(3600)
//                        secsLeft -= hh*3600
//                        val mm = secsLeft/60
//                        secsLeft -= mm*60
//                        val ss = secsLeft
//                        time4 = if(hh.toInt()/10==0) "0$hh" else "$hh"
//                        time4 += if (mm.toInt() / 10 == 0) ":0$mm" else ":$mm"
//                        time4 += if (ss.toInt() / 10 == 0) ":0$ss" else ":$ss"
//                        binding.mainTimer4.text = time4
//                    }
//                    override fun onFinish() {
//
//                    }
//                }.start()
//                setContentView(binding.root)
//            }
            setContentView(binding.root)
        }.addOnFailureListener {
            Toast.makeText(this, "Unable to connect to database!\nCheck internet connectivity", Toast.LENGTH_LONG).show()
        }
    }



    private fun intoInfo(socket: ImageView){
        socket.setOnClickListener {
            val intent:Intent = when(socket.id){
                R.id.socket1 -> Intent(this, Socket1Activity::class.java)
                R.id.socket2 -> Intent(this, Socket2Activity::class.java)
                R.id.socket3 -> Intent(this, Socket3Activity::class.java)
                else -> Intent(this, Socket4Activity::class.java)
            }
            startActivity(intent)
        }
    }


}