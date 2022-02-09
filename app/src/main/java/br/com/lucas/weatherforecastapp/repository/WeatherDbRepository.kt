package br.com.lucas.weatherforecastapp.repository

import br.com.lucas.weatherforecastapp.data.WeatherDao
import br.com.lucas.weatherforecastapp.model.Favorite
import br.com.lucas.weatherforecastapp.model.UnitSettings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()

    fun getUnitSettings(): Flow<List<UnitSettings>> = weatherDao.getUnitsSettings()
    suspend fun insertUnitSettings(unitSettings: UnitSettings) = weatherDao.insertUnitSettings(unitSettings)
    suspend fun updateUnitSettings(unitSettings: UnitSettings) = weatherDao.updateUnitSettings(unitSettings)
    suspend fun deleteUnitSettings(unitSettings: UnitSettings) = weatherDao.deleteUnitSettings(unitSettings)
    suspend fun deleteAllUnitsSettings() = weatherDao.deleteAllUnitSettings()
}