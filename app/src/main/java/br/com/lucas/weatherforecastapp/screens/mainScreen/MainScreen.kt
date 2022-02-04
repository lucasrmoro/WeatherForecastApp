package br.com.lucas.weatherforecastapp.screens.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.lucas.weatherforecastapp.R
import br.com.lucas.weatherforecastapp.data.DataOrException
import br.com.lucas.weatherforecastapp.model.WeatherObject
import br.com.lucas.weatherforecastapp.utils.formatDate
import br.com.lucas.weatherforecastapp.utils.formatDecimals
import br.com.lucas.weatherforecastapp.widgets.WeatherAppBar
import coil.compose.rememberImagePainter

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val weatherData =
        produceState<DataOrException<WeatherObject, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) { value = viewModel.getWeatherData(city = "uruguaiana") }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weatherObject = weatherData.data!!, navController = navController)
    }
}

@Composable
fun MainScaffold(weatherObject: WeatherObject, navController: NavController) {

    Scaffold(topBar = {
        WeatherAppBar(
            title = weatherObject.city.name + ", ${weatherObject.city.country}",
            navController = navController,
            elevation = 4.dp,
        )
    }) {
        MainContent(data = weatherObject)
    }
}

@Composable
fun MainContent(data: WeatherObject) {
    val weatherItem = data.list.first()
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather.first().icon}.png"

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
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                WeatherStateImage(imageUrl = imageUrl)

                Text(
                    text = weatherItem.temp.day.formatDecimals() + "°",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(text = weatherItem.weather.first().main, fontStyle = FontStyle.Italic)
            }
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = stringResource(R.string.label_weather_icon),
        modifier = Modifier.size(80.dp)
    )
}
