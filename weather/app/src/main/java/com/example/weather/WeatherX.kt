package com.example.weather


import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class WeatherX(
    val coord: JSONObject,
    val weather: List<WeatherXX>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: JSONObject,
    val clouds: JSONObject,
    val dt: Int,
    val sys: JSONObject,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)