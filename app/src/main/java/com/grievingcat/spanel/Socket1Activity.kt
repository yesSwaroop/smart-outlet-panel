package com.grievingcat.spanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.grievingcat.spanel.databinding.ActivitySocket1Binding
import com.grievingcat.spanel.fragments.ConsumptionFragment
import com.grievingcat.spanel.fragments.PowerFragment

class Socket1Activity : AppCompatActivity() {

    private val powerfragment = PowerFragment()
    private val consumptionfragment = ConsumptionFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socket1)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        replaceFragment(powerfragment)
        val binding = ActivitySocket1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bnav1.itemIconTintList = null
        binding.bnav1.setOnItemSelectedListener {
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
            transaction.replace(R.id.fragment_container1, fragment)
            transaction.commit()
            
        }
    }
}


