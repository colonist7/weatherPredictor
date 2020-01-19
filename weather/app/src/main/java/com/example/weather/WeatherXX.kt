package com.example.weather


import com.google.gson.annotations.SerializedName

data class WeatherXX(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)