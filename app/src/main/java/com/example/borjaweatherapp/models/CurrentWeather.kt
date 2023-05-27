package com.example.borjaweatherapp.models

import com.example.borjaweatherapp.models.common.*
import com.example.borjaweatherapp.network.BaseDo
import com.google.gson.annotations.SerializedName

class CurrentWeather(
    @field:SerializedName("coord")
    var coord: Coord,
    @field:SerializedName("weather")
    var weather: List<Weather>,
    @field:SerializedName("base")
    var base: String,
    @field:SerializedName("main")
    var main: Main,
    @field:SerializedName("visibility")
    var visibility: String,
    @field:SerializedName("wind")
    var wind: Wind,
    @field:SerializedName("clouds")
    var clouds: Clouds,
    @field:SerializedName("dt")
    var dt: Int,
    @field:SerializedName("sys")
    var sys: Sys,
    @field:SerializedName("timezone")
    var timezone: Int,
    @field:SerializedName("id")
    var id: String,
    @field:SerializedName("name")
    var name: String,
    @field:SerializedName("cod")
    var cod: String
) : BaseDo() {

}