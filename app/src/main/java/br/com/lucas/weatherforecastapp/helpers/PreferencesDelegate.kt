package br.com.lucas.weatherforecastapp.helpers

import android.content.Context

class PreferencesDelegate(val context: Context, prefs: String) {

    private val currentPrefs = context.getSharedPreferences(prefs, Context.MODE_PRIVATE)

    fun getPreference(key: String, defaultValue: Any): Any {
        with(currentPrefs) {
            val result: Any = when (defaultValue) {
                is Boolean -> getBoolean(key, defaultValue)
                is Int -> getInt(key, defaultValue)
                is Long -> getLong(key, defaultValue)
                is Float -> getFloat(key, defaultValue)
                is String -> getString(key, defaultValue) ?: "UNDEFINED"
                else -> throw IllegalArgumentException()
            }
            @Suppress("UNCHECKED_CAST")
            return result
        }
    }

    fun setPreference(key: String, value: Any) {
        with(currentPrefs.edit()) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is String -> putString(key, value)
                else -> throw IllegalArgumentException()
            }.apply()
        }
    }

    fun removePreference(key: String){
        currentPrefs.edit().remove(key).apply()
    }
}
