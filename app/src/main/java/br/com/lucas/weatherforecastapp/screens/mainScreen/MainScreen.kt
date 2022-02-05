package br.com.lucas.weatherforecastapp.screens.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.lucas.weatherforecastapp.R
import br.com.lucas.weatherforecastapp.data.DataOrException
import br.com.lucas.weatherforecastapp.model.WeatherItem
import br.com.lucas.weatherforecastapp.model.WeatherObject
import br.com.lucas.weatherforecastapp.ui.theme.Purple500
import br.com.lucas.weatherforecastapp.utils.formatDate
import br.com.lucas.weatherforecastapp.utils.formatDateTime
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

                WeatherStateImage(imageUrl = imageUrl, modifier = Modifier)

                Text(
                    text = weatherItem.temp.day.formatDecimals() + "°",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(text = weatherItem.weather.first().main, fontStyle = FontStyle.Italic)
            }
        }
        HumidityWindPressureRow(weather = weatherItem)
        Divider(Modifier.padding(bottom = 15.dp))
        SunsetSunriseRow(weather = weatherItem)
        WeekWeatherForecast(weatherForecastList = data.list)
    }
}

@Composable
fun WeekWeatherForecast(weatherForecastList: List<WeatherItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.label_this_week),
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )
        Surface(
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(3.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items = weatherForecastList) { weatherItem ->
                    WeatherDetailRow(weatherItem = weatherItem)
                }
            }
        }
    }
}

@Composable
fun WeatherDetailRow(weatherItem: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather.first().icon}.png"

    Surface(
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 33.dp,
                    bottomStart = 33.dp,
                    bottomEnd = 33.dp
                )
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                WeatherStateImage(imageUrl = imageUrl, modifier = Modifier.size(65.dp))
                Text(
                    text = "${
                        weatherItem.dt.formatDate().split(",")[0]
                    } · ${weatherItem.weather.first().description}",
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
            Row(
                modifier = Modifier.padding(horizontal = 6.dp)
            ) {
                Text(
                    text = "${weatherItem.temp.max.formatDecimals()}°",
                    style = TextStyle(color = Purple500, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "${weatherItem.temp.min.formatDecimals()}°",
                    style = TextStyle(color = Color.LightGray, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
fun SunsetSunriseRow(weather: WeatherItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(6.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = stringResource(
                    R.string.label_sunrise_icon
                ),
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = weather.sunrise.formatDateTime(),
                style = MaterialTheme.typography.caption
            )
        }
        Row(modifier = Modifier.padding(6.dp)) {
            Text(
                text = weather.sunset.formatDateTime(),
                style = MaterialTheme.typography.caption
            )
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = stringResource(
                    R.string.label_sunset_icon
                ),
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(6.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = stringResource(R.string.label_humidity_icon),
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.humidity}%", style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(6.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = stringResource(R.string.label_pressure_icon),
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.pressure} psi", style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(6.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = stringResource(R.string.label_wind_icon),
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.speed} mph", style = MaterialTheme.typography.caption)
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String, modifier: Modifier) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = stringResource(R.string.label_weather_icon),
        modifier = modifier.size(80.dp)
    )
}