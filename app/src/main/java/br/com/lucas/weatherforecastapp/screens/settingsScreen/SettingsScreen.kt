package br.com.lucas.weatherforecastapp.screens.settingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.lucas.weatherforecastapp.R
import br.com.lucas.weatherforecastapp.model.UnitSettings
import br.com.lucas.weatherforecastapp.widgets.WeatherAppBar

@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = stringResource(R.string.label_settings),
            navController = navController,
            icon = Icons.Default.ArrowBack,
            onButtonClicked = { navController.popBackStack() },
            isMainScreen = false
        )
    }) {
        val unitToggleState = remember { mutableStateOf(false) }
        val measureUnits = listOf("Imperial (F)", "Metric (C)")
        val choiceFromDb = settingsViewModel.unitSettingsList.collectAsState().value
        val defaultChoice =
            if (choiceFromDb.isNullOrEmpty()) measureUnits[0] else choiceFromDb[0].unit
        var choiceState by remember { mutableStateOf(defaultChoice) }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change Unit Of Measurement",
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                IconToggleButton(
                    checked = !unitToggleState.value, onCheckedChange = {
                        unitToggleState.value = !it
                        choiceState = if (unitToggleState.value) {
                            "Imperial (F)"
                        } else {
                            "Metric (C)"
                        }
                    }, modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(Color.Magenta.copy(alpha = 0.4f))
                ) {
                    Text(text = if (unitToggleState.value) "Fahrenheit °F" else "Celsius °C")
                }

                Button(
                    onClick = {
                        settingsViewModel.deleteAllUnitSettings()
                        settingsViewModel.insertUnitSettings(UnitSettings(unit = choiceState))
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFEFBE42))
                ) {
                    Text(
                        text = stringResource(R.string.label_save),
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}