package com.example.borjaweatherapp.ui.dashboard

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.borjaweatherapp.BaseScreen
import com.example.borjaweatherapp.R
import com.example.borjaweatherapp.databinding.ActivityDashboardScreenBinding

class DashBoardScreen : BaseScreen() {

    private lateinit var binding: ActivityDashboardScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard_screen)

        // bottom navigation menu
        setNavigationBar()
        replaceFragment(CurrentWeatherFragment())
    }

    private fun setNavigationBar() {
        binding.chipNavBar.setItemSelected(R.id.navigation_home)
        binding.chipNavBar.setOnItemSelectedListener { id ->
            run {
                when (id) {
                    R.id.navigation_home -> replaceFragment(CurrentWeatherFragment())
                    R.id.navigation_dashboard -> replaceFragment(WeatherListFragment())
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_left_to_right,
            R.anim.slide_right_to_left
        )
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
    }
}