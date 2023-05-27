package com.example.borjaweatherapp.viewmodel

import android.app.Activity
import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.borjaweatherapp.R
import com.example.borjaweatherapp.managers.SharedPreferenceManager
import com.example.borjaweatherapp.models.User
import com.example.borjaweatherapp.network.ResponseDO
import com.example.borjaweatherapp.network.TaskStatus
import java.util.ArrayList

class CompletionViewModel(
    application: Application,
    private val mActivity: Activity
    ) : AndroidViewModel(application) {
}