package com.example.borjaweatherapp.models.common

import com.example.borjaweatherapp.network.BaseDo
import com.google.gson.annotations.SerializedName

class Clouds(
    @field:SerializedName("all")
    var all: Int
    ): BaseDo() {

}