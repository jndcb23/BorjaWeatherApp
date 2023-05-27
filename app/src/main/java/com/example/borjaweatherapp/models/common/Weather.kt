package com.example.borjaweatherapp.models.common

import com.example.borjaweatherapp.network.BaseDo
import com.google.gson.annotations.SerializedName

class Weather(
    @field:SerializedName("id")
    var id: Int,
    @field:SerializedName("main")
    var main: String,
    @field:SerializedName("description")
    var description: String,
    @field:SerializedName("icon")
    var icon: String
    ): BaseDo() {

}