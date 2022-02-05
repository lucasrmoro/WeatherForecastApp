package br.com.lucas.weatherforecastapp.repository

import br.com.lucas.weatherforecastapp.data.WeatherDao
import br.com.lucas.weatherforecastapp.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun getFavoriteById(city: String) = weatherDao.getFavoriteById(city)
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()
}