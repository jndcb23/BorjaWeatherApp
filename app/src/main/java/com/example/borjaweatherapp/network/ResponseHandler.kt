package com.example.borjaweatherapp.network

import com.example.borjaweatherapp.parser.CurrentWeatherParser

abstract class ResponseHandler {

    abstract fun parse(isResponse: String)

    abstract fun getResponse(): ResponseDO

    companion object {
        fun getParser(serviceId: String, response: String): ResponseHandler? {

            if(serviceId.isNotEmpty()) {
                return CurrentWeatherParser(response, serviceId)
            }
            return null
        }
    }
}