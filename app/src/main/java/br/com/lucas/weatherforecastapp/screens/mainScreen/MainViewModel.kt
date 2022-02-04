package br.com.lucas.weatherforecastapp.screens.mainScreen

import androidx.lifecycle.ViewModel
import br.com.lucas.weatherforecastapp.data.DataOrException
import br.com.lucas.weatherforecastapp.model.WeatherObject
import br.com.lucas.weatherforecastapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    suspend fun getWeatherData(city: String): DataOrException<WeatherObject, Boolean, Exception>{
        return repository.getWeather(cityQuery = city)
    }
}