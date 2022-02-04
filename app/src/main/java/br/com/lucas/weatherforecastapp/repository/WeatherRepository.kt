package br.com.lucas.weatherforecastapp.repository

import br.com.lucas.weatherforecastapp.data.DataOrException
import br.com.lucas.weatherforecastapp.model.WeatherObject
import br.com.lucas.weatherforecastapp.network.WeatherApi

class WeatherRepository(private val api: WeatherApi) {

    suspend fun getWeather(cityQuery: String): DataOrException<WeatherObject, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery)
        } catch (e: Exception){
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

}