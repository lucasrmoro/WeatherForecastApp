package br.com.lucas.weatherforecastapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.lucas.weatherforecastapp.model.Favorite
import br.com.lucas.weatherforecastapp.model.UnitSettings

@Database(entities = [Favorite::class, UnitSettings::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}