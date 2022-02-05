package br.com.lucas.weatherforecastapp.data

import androidx.room.*
import br.com.lucas.weatherforecastapp.model.City
import br.com.lucas.weatherforecastapp.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM favorite_table")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorite_table WHERE city =:city")
    suspend fun getFavoriteById(city: City): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("DELETE FROM favorite_table")
    suspend fun deleteAllFavorites()
}