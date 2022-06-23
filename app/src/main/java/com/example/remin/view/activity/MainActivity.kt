package com.example.remin.view.activity

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.remin.R
import com.example.remin.presenter.activity.MainPresenter
import com.example.remin.view.display.MainDisplay
import kotlinx.android.synthetic.main.activity_main.*
import org.osmdroid.config.Configuration

class MainActivity : AppCompatActivity(), MainDisplay {
    private lateinit var navHost: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ctx: Context = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        setContentView(R.layout.activity_main)
        MainPresenter(this)
    }

    override fun initBottomNavigation() {
        val navController = findNavController(R.id.fragmentContainer)
        bottom_navigation.setupWithNavController(navController)
    }
}