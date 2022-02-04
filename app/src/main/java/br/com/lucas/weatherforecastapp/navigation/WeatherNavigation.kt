package br.com.lucas.weatherforecastapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.lucas.weatherforecastapp.screens.MainScreen
import br.com.lucas.weatherforecastapp.screens.WeatherSplashScreen

@Composable
fun  WeatherNavigation () {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name ){
        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }

        composable(WeatherScreens.MainScreen.name){
            MainScreen(navController = navController)
        }
    }
}