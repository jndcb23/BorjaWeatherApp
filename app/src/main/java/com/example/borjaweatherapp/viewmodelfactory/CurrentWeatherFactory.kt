package com.example.borjaweatherapp.viewmodelfactory

import android.app.Activity
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.borjaweatherapp.viewmodel.CurrentWeatherViewModel

class CurrentWeatherFactory(private val mApplication: Application,
                            private val mActivity: Activity) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(mApplication, mActivity) as T
    }
}