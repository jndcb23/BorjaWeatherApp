package com.example.borjaweatherapp.parser

import com.example.borjaweatherapp.models.CurrentWeather
import com.example.borjaweatherapp.network.ResponseDO
import com.example.borjaweatherapp.network.ResponseHandler
import com.google.gson.Gson
import org.json.JSONObject

class CurrentWeatherParser(
    response: String,
    serviceId: String
) : ResponseHandler() {

    private val responseData: String = response
    private val service: String = serviceId
    private var responseDo: ResponseDO = ResponseDO()
    private lateinit var responseModel: CurrentWeather

    init {
        parse(responseData)
    }

    override fun parse(isResponse: String) {
        responseDo.service = service

        try {
            if (isResponse.isEmpty() && isResponse.startsWith("<")) {
                // data issue
                responseDo.isError = true
            } else if (isResponse.equals("TIMEDOUT", ignoreCase = true)) {
                // Timed Out Scenario
                responseDo.isError = true
            } else {
                responseDo.isError = false
                val gSon = Gson()
                responseModel = gSon.fromJson(isResponse, CurrentWeather::class.java)

                if (responseModel.cod.equals("200", ignoreCase = true)) {
                    responseDo.isError = false
                    responseDo.responseData = responseModel
                    responseDo.responseCode = responseModel.cod
                    return
                } else {
                    responseDo.isError = true
                    responseDo.errorData = responseModel
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            responseDo.isError = true
        }
    }

    override fun getResponse(): ResponseDO {
        return responseDo
    }
}