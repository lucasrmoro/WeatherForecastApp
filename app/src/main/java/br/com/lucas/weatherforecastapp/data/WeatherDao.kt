package br.com.lucas.weatherforecastapp.data

import androidx.room.*
import br.com.lucas.weatherforecastapp.model.Favorite
import br.com.lucas.weatherforecastapp.model.UnitSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM favorite_table")
    fun getFavorites(): Flow<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("DELETE FROM favorite_table")
    suspend fun deleteAllFavorites()

    // Unit Settings table
    @Query("SELECT * FROM settings_table")
    fun getUnitsSettings(): Flow<List<UnitSettings>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnitSettings(unitSettings: UnitSettings)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnitSettings(unitSettings: UnitSettings)

    @Delete
    suspend fun deleteUnitSettings(unitSettings: UnitSettings)

    @Query("DELETE FROM settings_table")
    suspend fun deleteAllUnitSettings()
}