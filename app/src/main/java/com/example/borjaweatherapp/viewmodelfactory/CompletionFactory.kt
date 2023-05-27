package com.example.borjaweatherapp.viewmodelfactory

import android.app.Activity
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.borjaweatherapp.viewmodel.CompletionViewModel

class CompletionFactory(private val mApplication: Application,
                        private val mActivity: Activity) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CompletionViewModel(mApplication, mActivity) as T
    }
}