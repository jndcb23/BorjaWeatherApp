package com.example.borjaweatherapp.network

import com.example.borjaweatherapp.models.CurrentWeather
import com.example.borjaweatherapp.models.common.Weather
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class NetworkManager {

    companion object {
        private var retrofit: Retrofit? = null
        private var gsonApi: ApiService? = null

        private fun apiStatic(): ApiService? {
            return gsonApi
        }

        private fun getClient(baseUrl: String): Retrofit? {

            if (retrofit == null) {
                val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }

            return retrofit
        }

        fun postRequest(url: String, cityName: String, appId: String): String? {
            val result: String?
            var call: Call<String>

            try {
                gsonApi = getClient(url)?.create(ApiService::class.java)
                call = apiStatic()!!.requestCurrentWeather(cityName, appId)
            } catch (e: Exception) {
                e.printStackTrace()
                gsonApi = getClient(url)?.create(ApiService::class.java)
                call = apiStatic()!!.requestCurrentWeather(cityName, appId)
            }

            result = try {
                val response = call.execute()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    "TIMEDOUT" + response.body()
                }
            } catch (e: IOException) {
                "TIMEDOUT"
            }
            return result
        }
    }
}