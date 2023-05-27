package com.example.borjaweatherapp.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.borjaweatherapp.R
import com.example.borjaweatherapp.managers.SharedPreferenceManager
import com.example.borjaweatherapp.models.User

class RegistrationViewModel(
    application: Application,
    private val mActivity: Activity
    ) : AndroidViewModel(application) {

    private var navigateToCompletion: MutableLiveData<Boolean> = MutableLiveData()
    private var errorMessage: MutableLiveData<String> = MutableLiveData()

    fun navigateToSuccessScreen(): MutableLiveData<Boolean> {
        return navigateToCompletion
    }

    fun getErrorMessage(): MutableLiveData<String> {
        return errorMessage
    }

    fun validateUserInfo(
        firstName: String,
        lastName: String,
        mobileNumber: String,
        pin: CharSequence
    ) {
        if (firstName.isEmpty() || lastName.isEmpty() || mobileNumber.isEmpty()) {
            errorMessage.postValue(mActivity.resources.getString(R.string.all_fields_mandatory))
        } else if (mobileNumber.length < 8) {
            errorMessage.postValue(mActivity.resources.getString(R.string.enter_valid_user_id))
        } else if(pin.length < 5) {
            errorMessage.postValue(mActivity.resources.getString(R.string.enter_valid_m_pin))
        } else {
            val dataToStore = mutableListOf<User>()
            val usersStored = SharedPreferenceManager.getArrayList(mActivity, mActivity.getString(R.string.user_list))
            val newUser = User(firstName, lastName, mobileNumber, pin.toString())
            usersStored?.let {
                dataToStore.addAll(usersStored as Collection<User>)
            }
            dataToStore.add(newUser)
            SharedPreferenceManager.saveUserArrayList(
                mActivity,
                mActivity.getString(R.string.user_list),
                dataToStore
            )

            navigateToCompletion.postValue(true)
        }
    }
}