package com.example.weather.data.model

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)