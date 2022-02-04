package br.com.lucas.weatherforecastapp.model

data class WeatherObject(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherItem>,
    val message: Double
)