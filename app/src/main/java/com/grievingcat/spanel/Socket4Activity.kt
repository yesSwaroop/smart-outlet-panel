package com.grievingcat.spanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.grievingcat.spanel.databinding.ActivitySocket1Binding
import com.grievingcat.spanel.databinding.ActivitySocket4Binding
import com.grievingcat.spanel.fragments.ConsumptionFragment
import com.grievingcat.spanel.fragments.PowerFragment

class Socket4Activity : AppCompatActivity() {

    private val powerfragment = PowerFragment()
    private val consumptionfragment = ConsumptionFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socket4)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val binding = ActivitySocket4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bnav4.itemIconTintList = null
        replaceFragment(powerfragment)
        binding.bnav4.setOnItemSelectedListener {
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
            transaction.replace(R.id.fragment_container4, fragment)
            transaction.commit()
        }
    }
}