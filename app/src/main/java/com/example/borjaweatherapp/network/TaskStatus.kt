package com.example.borjaweatherapp.network

interface TaskStatus {

    fun onTaskStarted(serviceId: String)
    fun onTaskCompleted(response: ResponseDO, serviceId: String)
}