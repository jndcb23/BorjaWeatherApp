package com.example.borjaweatherapp.models.common

import com.example.borjaweatherapp.network.BaseDo
import com.google.gson.annotations.SerializedName

class Main(
    @field:SerializedName("temp")
    var temp: Double,
    @field:SerializedName("feels_like")
    var feels_like: Double,
    @field:SerializedName("temp_min")
    var temp_min: Double,
    @field:SerializedName("temp_max")
    var temp_max: Double,
    @field:SerializedName("pressure")
    var pressure: Double,
    @field:SerializedName("humidity")
    var humidity: Double
    ): BaseDo() {

}