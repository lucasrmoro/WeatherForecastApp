package br.com.lucas.weatherforecastapp.screens.mainScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.lucas.weatherforecastapp.data.DataOrException
import br.com.lucas.weatherforecastapp.model.WeatherObject
import br.com.lucas.weatherforecastapp.navigation.WeatherScreens
import br.com.lucas.weatherforecastapp.screens.settingsScreen.SettingsViewModel
import br.com.lucas.weatherforecastapp.ui.theme.CircleColor
import br.com.lucas.weatherforecastapp.utils.formatDate
import br.com.lucas.weatherforecastapp.utils.formatDecimals
import br.com.lucas.weatherforecastapp.utils.getImageUrl
import br.com.lucas.weatherforecastapp.widgets.*

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {
    val isImperial by remember {
        mutableStateOf(
            settingsViewModel.unitMeasureSetting.split(" ")[0].lowercase() == "imperial"
        )
    }

    val weatherData =
        produceState<DataOrException<WeatherObject, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = viewModel.getWeatherData(
                city = city!!,
                units = settingsViewModel.unitMeasureSetting.split(" ")[0].lowercase()
            )
        }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(
            weatherObject = weatherData.data!!,
            navController = navController,
            isImperial = isImperial
        )
    }
}

@Composable
fun MainScaffold(weatherObject: WeatherObject, navController: NavController, isImperial: Boolean) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = weatherObject.city.name + ", ${weatherObject.city.country}",
            navController = navController,
            elevation = 4.dp,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            }
        )
    }) {
        MainContent(data = weatherObject, isImperial = isImperial)
    }
}

@Composable
fun MainContent(data: WeatherObject, isImperial: Boolean) {
    val weatherItem = data.list.first()

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = weatherItem.dt.formatDate(),
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = if (MaterialTheme.colors.isLight) CircleColor else CircleColor.copy(.85f)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                WeatherStateImage(
                    imageUrl = getImageUrl(weatherItem.weather.first().icon),
                    modifier = Modifier
                )

                Text(
                    text = weatherItem.temp.day.formatDecimals() + "°",
                    color = Color.Black,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(text = weatherItem.weather.first().main, fontStyle = FontStyle.Italic)
            }
        }
        HumidityWindPressureRow(weather = weatherItem, isImperial = isImperial)
        Divider(Modifier.padding(bottom = 15.dp))
        SunsetSunriseRow(weather = weatherItem)
        WeekWeatherForecast(weatherForecastList = data.list)
    }
}