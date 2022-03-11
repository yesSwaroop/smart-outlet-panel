package com.grievingcat.spanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.grievingcat.spanel.databinding.ActivitySocket2Binding
import com.grievingcat.spanel.fragments.ConsumptionFragment
import com.grievingcat.spanel.fragments.PowerFragment

class Socket2Activity : AppCompatActivity() {

    private val powerfragment = PowerFragment()
    private val consumptionfragment = ConsumptionFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socket2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val binding = ActivitySocket2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bnav2.itemIconTintList = null
        replaceFragment(powerfragment)
        binding.bnav2.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_power -> replaceFragment(powerfragment)
                R.id.ic_consumption -> replaceFragment(consumptionfragment)
            }
            true
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container2, fragment)
            transaction.commit()
        }
    }
}