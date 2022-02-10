package br.com.lucas.weatherforecastapp.screens.settingsScreen

import androidx.lifecycle.ViewModel
import br.com.lucas.weatherforecastapp.helpers.PreferencesDelegate
import br.com.lucas.weatherforecastapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val preferencesDelegate: PreferencesDelegate) :
    ViewModel() {

    var unitMeasureSetting: String
        set(value) {
            preferencesDelegate.setPreference(Constants.UNIT_MEASURE_SETTING_KEY, value)
        }
        get() = preferencesDelegate.getPreference(
            Constants.UNIT_MEASURE_SETTING_KEY,
            "Metric (C)"
        ) as String? ?: "Metric (C)"

    fun clearUnitMeasureSetting() =
        preferencesDelegate.removePreference(Constants.UNIT_MEASURE_SETTING_KEY)
}