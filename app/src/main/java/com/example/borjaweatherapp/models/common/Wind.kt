package com.example.borjaweatherapp.models.common

import com.example.borjaweatherapp.network.BaseDo
import com.google.gson.annotations.SerializedName

class Wind(
    @field:SerializedName("speed")
    var speed: Double,
    @field:SerializedName("deg")
    var deg: Double,
    @field:SerializedName("gust")
    var gust: Double
    ): BaseDo() {

}