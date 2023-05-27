package com.example.borjaweatherapp.models.common

import com.example.borjaweatherapp.network.BaseDo
import com.google.gson.annotations.SerializedName

class Sys(
    @field:SerializedName("type")
    var type: Int,
    @field:SerializedName("id")
    var id: Int,
    @field:SerializedName("country")
    var country: String,
    @field:SerializedName("sunrise")
    var sunrise: Int,
    @field:SerializedName("sunset")
    var sunset: Int
    ): BaseDo() {

}