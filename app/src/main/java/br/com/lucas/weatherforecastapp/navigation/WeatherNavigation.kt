package br.com.lucas.weatherforecastapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import br.com.lucas.weatherforecastapp.screens.aboutScreen.AboutScreen
import br.com.lucas.weatherforecastapp.screens.favoritesScreen.FavoritesScreen
import br.com.lucas.weatherforecastapp.screens.mainScreen.MainScreen
import br.com.lucas.weatherforecastapp.screens.searchScreen.SearchScreen
import br.com.lucas.weatherforecastapp.screens.settingsScreen.SettingsScreen
import br.com.lucas.weatherforecastapp.screens.splashScreen.WeatherSplashScreen

@ExperimentalComposeUiApi
@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable(
            "$route/{city}", arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                })
        ) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                MainScreen(navController = navController, city = city)
            }
        }
        composable(WeatherScreens.SearchScreen.name){
            SearchScreen(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }

        composable(WeatherScreens.FavoritesScreen.name) {
            FavoritesScreen(navController = navController)
        }

        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }


    }
}