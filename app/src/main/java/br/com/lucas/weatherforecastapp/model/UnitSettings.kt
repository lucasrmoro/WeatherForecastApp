package br.com.lucas.weatherforecastapp.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_table")
data class UnitSettings(
    @NonNull
    @PrimaryKey
    @ColumnInfo
    val unit: String
)
