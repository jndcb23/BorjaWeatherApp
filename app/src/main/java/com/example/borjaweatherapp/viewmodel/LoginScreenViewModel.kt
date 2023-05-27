package com.example.borjaweatherapp.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.borjaweatherapp.R
import com.example.borjaweatherapp.managers.SharedPreferenceManager

class LoginScreenViewModel(
    application: Application,
    private val mActivity: Activity
    ) : AndroidViewModel(application) {

    private var boolShowLoader: MutableLiveData<Boolean> = MutableLiveData()
    private var navigateToHome: MutableLiveData<Boolean> = MutableLiveData()
    private var errorMessage: MutableLiveData<String> = MutableLiveData()

    fun showLoader(): MutableLiveData<Boolean> {
        return boolShowLoader
    }

    fun validateInputData(userIdNum: String, pin: CharSequence) {
        when {
            (userIdNum.length < 8) -> {
                errorMessage.postValue(mActivity.resources.getString(R.string.enter_valid_user_id))
            }
            pin.length < 5 -> {
                errorMessage.postValue(mActivity.resources.getString(R.string.enter_valid_m_pin))
            }
            else -> {
                val userStored = SharedPreferenceManager.getUserArrayList(mActivity, mActivity.getString(R.string.user_list))
                userStored?.forEach { user ->
                    if (user?.userId.equals(userIdNum) && user?.mPin.equals(pin.toString())) {
                        navigateToHome.postValue(true)
                        return
                    }
                }
                errorMessage.postValue(mActivity.resources.getString(R.string.enter_valid_acc))
            }
        }
    }

    fun navigateToHomeScreen(): MutableLiveData<Boolean> {
        return navigateToHome
    }

    fun getErrorMessage(): MutableLiveData<String> {
        return errorMessage
    }
}