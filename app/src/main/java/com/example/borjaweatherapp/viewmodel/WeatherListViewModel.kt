package com.example.borjaweatherapp.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class WeatherListViewModel(
    application: Application,
    private val mActivity: Activity
    ) : AndroidViewModel(application) {

    private var boolShowLoader: MutableLiveData<Boolean> = MutableLiveData()

    fun showLoader(): MutableLiveData<Boolean> {
        return boolShowLoader
    }
}