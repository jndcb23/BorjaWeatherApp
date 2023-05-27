package com.example.borjaweatherapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.borjaweatherapp.databinding.ActivityMainBinding
import com.example.borjaweatherapp.managers.SharedPreferenceManager
import com.example.borjaweatherapp.ui.LoginScreen
import com.example.borjaweatherapp.ui.customdialog.BasicDialog

abstract class BaseScreen : AppCompatActivity(), BasicDialog.BasicDialogListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_BorjaWeatherApp)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SharedPreferenceManager.initialize(this)
    }

    override fun onOkButtonClicked(tag: String) {
        super.onOkButtonClicked(tag)
        if (tag.equals(getString(R.string.current_weather), ignoreCase = true)) {
            startActivity(Intent(applicationContext, LoginScreen::class.java))
        } else if (tag.equals(getString(R.string.weather_list), ignoreCase = true)) {
            startActivity(Intent(applicationContext, LoginScreen::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        BasicDialog.basicDialogListener = this
    }

    override fun onPause() {
        super.onPause()
        BasicDialog.basicDialogListener = null
    }

    override fun onBackPressed() {
    }
}