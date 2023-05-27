package com.example.borjaweatherapp.models.common

import com.example.borjaweatherapp.network.BaseDo
import com.google.gson.annotations.SerializedName

class Coord(
    @field:SerializedName("lon")
    var lon: Double,
    @field:SerializedName("lat")
    var lat: Double
    ): BaseDo() {

}