package com.example.tiprankstask.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.tiprankstask.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpView()
    }

    private fun setUpView() {
        supportFragmentManager.findFragmentById(R.id.fragmentNavHost) as NavHostFragment
    }
}