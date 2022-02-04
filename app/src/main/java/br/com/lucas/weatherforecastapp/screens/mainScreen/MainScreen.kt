package br.com.lucas.weatherforecastapp.screens.mainScreen

import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.lucas.weatherforecastapp.data.DataOrException
import br.com.lucas.weatherforecastapp.model.WeatherObject
import br.com.lucas.weatherforecastapp.widgets.WeatherAppBar

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val weatherData =
        produceState<DataOrException<WeatherObject, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) { value = viewModel.getWeatherData(city = "Seattle") }.value

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

    Text(text = data.city.name)


}
