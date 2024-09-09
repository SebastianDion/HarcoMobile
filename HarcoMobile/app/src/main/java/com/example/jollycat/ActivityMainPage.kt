package com.example.jollycat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.jollycat.databinding.ActivityMainPageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityMainPage : AppCompatActivity() {
    private lateinit var binding: ActivityMainPageBinding
    private val global = Global
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        createSection(FragmentHome())
        val bottomNavBar = binding.navbar as BottomNavigationView
        bottomNavBar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    createSection(FragmentHome())
                    true
                }
                R.id.cart -> {
                    createSection(FragmentCart())
                    true
                }
                R.id.profile -> {
                    createSection(FragmentProfile())
                    true
                }
                R.id.about -> {
                    createSection(FragmentAbout())
                    true
                }
                else -> {
                    false
                }
            }
        }

    }
    private  fun createSection(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contentContainer,fragment)
        transaction.commit()
    }
}