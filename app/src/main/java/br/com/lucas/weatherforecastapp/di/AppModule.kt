package br.com.lucas.weatherforecastapp.di

import br.com.lucas.weatherforecastapp.network.WeatherApi
import br.com.lucas.weatherforecastapp.repository.WeatherRepository
import br.com.lucas.weatherforecastapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(weatherApi: WeatherApi): WeatherRepository = WeatherRepository(weatherApi)
}