package com.example.uas_travelapp.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.uas_travelapp.Adapter.TabAdapter
import com.example.uas_travelapp.R
import com.example.uas_travelapp.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }
    lateinit var viewPager2: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewPager2 = binding.viewPager2

        val sharedPref = this.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        if (sharedPref.contains("email")) {
            val role = sharedPref.getString("role", "user")
            if (role == "user") {
                val intent = Intent(this, DashboardUserActivity::class.java)
                startActivity(intent)
            }
            else {
                val intent = Intent(this, DashboardAdminActivity::class.java)
                startActivity(intent)
            }
        }

        with(binding) {
            viewPager2.adapter = TabAdapter(this@HomeActivity)
            TabLayoutMediator(tabLayout, viewPager2) { tab, pos ->
                tab.text = when (pos) {
                    0 -> "Login"
                    1 -> "Register"
                    else -> ""
                }
            }.attach()
        }
    }
}