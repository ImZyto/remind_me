package com.example.remin.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.remin.R
import com.example.remin.presenter.activity.MainPresenter
import com.example.remin.view.display.MainDisplay
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainDisplay {
    private lateinit var navHost: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHost = findNavController(R.id.nav_main)
        MainPresenter(this)
    }

    override fun initBottomNavigation() {
        val appBarConfiguration = AppBarConfiguration(setOf())
        bottom_navigation.setupWithNavController(navHost)
    }
}