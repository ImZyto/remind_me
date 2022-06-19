package com.example.remin.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import com.example.remin.R
import android.preference.PreferenceManager
import org.osmdroid.config.Configuration


class MainActivity : AppCompatActivity() {
    private lateinit var navHost: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ctx: Context = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        setContentView(R.layout.activity_main)


    }
}