package com.example.borjaweatherapp.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import android.util.Log
import com.example.borjaweatherapp.utils.AppConstants

class NetworkRequest(
        serviceId: String,
        cityName: String,
        appId: String,
        taskStatus: TaskStatus
) {
    private var service: String = serviceId
    private var cityName: String = cityName
    private var appId: String = appId
    private var mTaskStatus: TaskStatus = taskStatus
    private var responseDo: ResponseDO = ResponseDO()

    // Coroutine for making network request
    private val parentJob = Job()
    private val coRoutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO
    private val scope = CoroutineScope(coRoutineContext)

    init {
        setTaskStartedListener()
        scope.launch {
            makeNetworkRequest()
        }
    }

    private fun makeNetworkRequest() {
        try {
            Log.v("NetworkRequest", appId)
            val response: String = if (!AppConstants.isEncryptionEnabled) {
                NetworkManager.postRequest(
                    AppConstants.SERVER_URL,
                    cityName,
                    appId
                )!!
            } else {
                NetworkManager.postRequest(
                    AppConstants.SERVER_URL,
                    cityName,
                    appId
                )!!
            }

            if (response == null) {
                Log.v("NetworkResponse", "null value")
            }else {
                val responseHandler = ResponseHandler.getParser(service, response)
                if (responseHandler != null) {
                    responseDo = responseHandler.getResponse()
                }
                Log.v("NetworkResponse", response)
            }

            setTaskCompletedListener(responseDo)
        } catch (e: Exception) {
            Log.v("NetworkResponse", e.toString())
            e.printStackTrace()
            responseDo.isError = true
            setTaskCompletedListener(responseDo)
        }
    }

    private fun setTaskStartedListener() {
        mTaskStatus.onTaskStarted(service)
    }

    private fun setTaskCompletedListener(response: ResponseDO) {
        mTaskStatus.onTaskCompleted(response, service)
    }
}