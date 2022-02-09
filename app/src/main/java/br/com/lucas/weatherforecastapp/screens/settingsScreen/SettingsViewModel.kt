package br.com.lucas.weatherforecastapp.screens.settingsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lucas.weatherforecastapp.model.UnitSettings
import br.com.lucas.weatherforecastapp.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository) :
    ViewModel() {

    private val _unitSettingsList = MutableStateFlow<List<UnitSettings>>(emptyList())
    val unitSettingsList = _unitSettingsList.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getUnitSettings().distinctUntilChanged()
                .collect { listOfUnitSettings ->
                    if (listOfUnitSettings.isNullOrEmpty()) {
                        Log.d("listOfUnitSettings", "listOfUnitSettings Empty")
                    } else {
                        _unitSettingsList.value = listOfUnitSettings
                    }
                }
        }
    }

    fun insertUnitSettings(unitSettings: UnitSettings) = viewModelScope.launch {
        repository.insertUnitSettings(unitSettings)
    }

    fun updateUnitSettings(unitSettings: UnitSettings) = viewModelScope.launch {
        repository.updateUnitSettings(unitSettings)
    }

    fun deleteUnitSettings(unitSettings: UnitSettings) = viewModelScope.launch {
        repository.deleteUnitSettings(unitSettings)
    }

    fun deleteAllUnitSettings() = viewModelScope.launch {
        repository.deleteAllUnitsSettings()
    }
}