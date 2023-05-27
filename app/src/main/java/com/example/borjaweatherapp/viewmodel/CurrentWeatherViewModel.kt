package com.example.borjaweatherapp.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.borjaweatherapp.R
import com.example.borjaweatherapp.managers.SharedPreferenceManager
import com.example.borjaweatherapp.models.CurrentWeather
import com.example.borjaweatherapp.network.NetworkRequest
import com.example.borjaweatherapp.network.ResponseDO
import com.example.borjaweatherapp.network.TaskStatus
import com.example.borjaweatherapp.utils.CommonHelper

class CurrentWeatherViewModel(
    application: Application,
    private val mActivity: Activity): AndroidViewModel(application), TaskStatus {

    private var boolShowLoader: MutableLiveData<Boolean> = MutableLiveData()
    private var city: MutableLiveData<String> = MutableLiveData()
    private var country: MutableLiveData<String> = MutableLiveData()
    private var temperature: MutableLiveData<Int> = MutableLiveData()
    private var sunriseTime: MutableLiveData<String> = MutableLiveData()
    private var sunsetTime: MutableLiveData<String> = MutableLiveData()
    private var currentWeather: MutableLiveData<String> = MutableLiveData()
    private var timeFetched: MutableLiveData<String> = MutableLiveData()
    private var errorMessage: MutableLiveData<String> = MutableLiveData()

    fun showLoader(): MutableLiveData<Boolean> {
        return boolShowLoader
    }

    override fun onTaskStarted(serviceId: String) {
        boolShowLoader.postValue(true)
    }

    override fun onTaskCompleted(response: ResponseDO, serviceId: String) {
        if (!response.isError) {
            val currentWeatherResponse = response.responseData as (CurrentWeather)
            if (currentWeatherResponse.cod == "200") {
                responseSaving(currentWeatherResponse)
                city.postValue(currentWeatherResponse.name)
                country.postValue(currentWeatherResponse.sys.country)
                temperature.postValue(CommonHelper.kelvinToCelsius(currentWeatherResponse.main.temp))
                sunriseTime.postValue(CommonHelper.utcTimeStampToLocal(
                    currentWeatherResponse.sys.sunrise, currentWeatherResponse.timezone))
                sunsetTime.postValue(CommonHelper.utcTimeStampToLocal(
                    currentWeatherResponse.sys.sunset, currentWeatherResponse.timezone))
                timeFetched.postValue(CommonHelper.utcTimeStampToLocal(
                    currentWeatherResponse.dt, currentWeatherResponse.timezone))
                currentWeather.postValue(currentWeatherResponse.weather[0].main)
            }
        } else {
            errorMessage.postValue(mActivity.resources.getString(R.string.no_result))
        }

        boolShowLoader.postValue(false)
    }

    private fun responseSaving(currentWeatherResponse: CurrentWeather) {
        val dataToStore = mutableListOf<CurrentWeather>()
        val dataStored = SharedPreferenceManager.getArrayList(mActivity, mActivity.getString(R.string.data_list))
        dataStored?.let {
            dataToStore.addAll(dataStored as Collection<CurrentWeather>)
        }
        dataToStore.add(currentWeatherResponse)

        SharedPreferenceManager.saveArrayList(
            mActivity,
            mActivity.getString(R.string.data_list),
            dataToStore
        )
    }

    fun validateInputData(activity: Activity, inputValue: String) {
        if (inputValue.isNotEmpty()) {
            NetworkRequest(
                activity.getString(R.string.current_weather),
                inputValue,
                activity.getString(R.string.api_key),
                this
            )
        } else {
            errorMessage.postValue(mActivity.resources.getString(R.string.enter_valid_city))
        }
    }

    fun getCity(): MutableLiveData<String> {
        return city
    }

    fun getCountry(): MutableLiveData<String> {
        return country
    }

    fun getTemperature(): MutableLiveData<Int> {
        return temperature
    }

    fun getSunrise(): MutableLiveData<String> {
        return sunriseTime
    }

    fun getSunset(): MutableLiveData<String> {
        return sunsetTime
    }

    fun getCurrentWeather(): MutableLiveData<String> {
        return currentWeather
    }

    fun getTimeFetched(): MutableLiveData<String> {
        return timeFetched
    }

    fun getErrorMessage(): MutableLiveData<String> {
        return errorMessage
    }
}