package com.myprojects.my_weather_app.data.model

data class WeatherResponse(
    val coord: Coordinates,
    val weather: List<Weather>,
    val main: Main,
    val name: String,
    val dt: Long
)

data class Coordinates(val lon: Double, val lat: Double)
data class Weather(val id: Int, val main: String, val description: String, val icon: String)
data class Main(val temp: Double, val feels_like: Double, val temp_min: Double, val temp_max: Double, val pressure: Int, val humidity: Int)