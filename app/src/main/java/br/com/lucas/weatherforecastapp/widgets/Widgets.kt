package br.com.lucas.weatherforecastapp.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.lucas.weatherforecastapp.R
import br.com.lucas.weatherforecastapp.model.Favorite
import br.com.lucas.weatherforecastapp.model.WeatherItem
import br.com.lucas.weatherforecastapp.navigation.WeatherScreens
import br.com.lucas.weatherforecastapp.screens.favoritesScreen.FavoriteViewModel
import br.com.lucas.weatherforecastapp.utils.formatDate
import br.com.lucas.weatherforecastapp.utils.formatDateTime
import br.com.lucas.weatherforecastapp.utils.formatDecimals
import coil.compose.rememberImagePainter

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
                    style = TextStyle(color = Color.Blue.copy(0.7f), fontWeight = FontWeight.Bold)
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
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean) {
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
            Text(
                text = "${weather.speed} " + if (isImperial) "mph" else "m/s",
                style = MaterialTheme.typography.caption
            )
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

@Composable
fun WeatherAppBar(
    title: String,
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    val showToast = remember { mutableStateOf(false) }

    val context = LocalContext.current

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colors.onSecondary,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = { onAddActionClicked() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.label_search_icon)
                    )
                }
                IconButton(onClick = { showDialog.value = true }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = stringResource(R.string.label_more_icon)
                    )
                }
            }
        },
        navigationIcon = {
            if (icon != null) {
                IconButton(onClick = { onButtonClicked() }) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            }

            if (isMainScreen) {
                val dataList = title.split(",")

                val isAlreadyFavList = favoriteViewModel
                    .favList.collectAsState().value.filter { item ->
                        (item.city == title.split(",")[0])
                    }

                if (!isAlreadyFavList.isNullOrEmpty()) {
                    showToast.value = false
                    Box {}
                } else {
                    IconButton(onClick = {
                        favoriteViewModel.insertFavorite(
                            Favorite(
                                city = dataList[0],
                                country = dataList[1]
                            )
                        ).run {
                            showToast.value = true
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = stringResource(R.string.label_favorite_icon),
                            modifier = Modifier.scale(0.9f)
                        )
                    }
                }
                ShowToast(context = context, showIt = showToast)
            }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation
    )
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value) {
        Toast.makeText(
            context,
            stringResource(R.string.label_added_to_favorites),
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController
) {
    val items = listOf("About", "Favorites", "Settings")
    var expanded by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                showDialog.value = false
            },
            modifier = Modifier
                .background(color = MaterialTheme.colors.onPrimary)
                .width(150.dp)
        ) {
            items.forEach { menuText ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                }) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(
                                when (menuText) {
                                    "About" -> WeatherScreens.AboutScreen.name
                                    "Favorites" -> WeatherScreens.FavoritesScreen.name
                                    else -> WeatherScreens.SettingsScreen.name
                                }
                            )
                        }
                    ) {
                        Icon(
                            imageVector = when (menuText) {
                                "About" -> Icons.Default.Info
                                "Favorites" -> Icons.Default.FavoriteBorder
                                else -> Icons.Default.Settings
                            },
                            contentDescription = null,
                            tint = Color.LightGray
                        )
                        Text(
                            text = menuText, fontWeight = FontWeight.W300
                        )
                    }
                }
            }
        }
    }
}


