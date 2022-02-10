package br.com.lucas.weatherforecastapp.screens.settingsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.lucas.weatherforecastapp.R
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
        val measureUnits = listOf("Imperial (F)", "Metric (C)")
        var selectedItem by remember {
            mutableStateOf(
                if (settingsViewModel.unitMeasureSetting == "imperial")
                    measureUnits[0] else measureUnits[1]
            )
        }
        var expanded by remember { mutableStateOf(false) }
        val icon = if (expanded) {
            Icons.Filled.KeyboardArrowUp
        } else {
            Icons.Filled.KeyboardArrowDown
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column {
                    val menuFieldWidth = 200.dp
                    OutlinedTextField(value = selectedItem, onValueChange = { selectedItem = it },
                        modifier = Modifier
                            .width(menuFieldWidth)
                            .focusable(false)
                            .clickable(
                                onClick = {
                                    expanded = !expanded
                                }
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = MaterialTheme.colors.onSurface,
                            disabledLabelColor = MaterialTheme.colors.onSurface
                        ),
                        enabled = false,
                        label = { Text(text = stringResource(R.string.label_unit_measure)) },
                        trailingIcon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = null
                            )
                        })

                    DropdownMenu(
                        expanded = expanded, onDismissRequest = { expanded = false },
                        modifier = Modifier.width(menuFieldWidth)
                    ) {
                        measureUnits.forEach { label ->
                            DropdownMenuItem(onClick = {
                                selectedItem = label
                                expanded = false
                            }) {
                                Text(text = label)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        settingsViewModel.clearUnitMeasureSetting()
                        settingsViewModel.unitMeasureSetting =
                            selectedItem.split(" ")[0].lowercase()
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