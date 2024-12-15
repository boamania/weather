package com.example.weather.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weather.R
import com.example.weather.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val state by viewModel.weatherState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(stringResource(R.string.enter_city)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    coroutineScope.launch {
                        viewModel.fetchWeather(searchQuery)
                    }
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.fetchWeather(searchQuery)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }
            state.error != null -> {
                Text(
                    text = "Error: ${state.error}",
                    color = MaterialTheme.colorScheme.error
                )
            }
            state.weatherData != null -> {
                state.weatherData?.let { weather ->
                    Text(
                        text = "City: ${weather.name}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Temperature: ${weather.main.temp}°C",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        weather.weather.firstOrNull()?.icon?.let { icon ->
                            // Display the icon
                            Image(
                                painter = painterResource(id = getWeatherIconResource(icon)),
                                contentDescription = "Weather Icon",
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(
                            text = "Condition: ${weather.weather.firstOrNull()?.description ?: "Unknown"}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

// icon name to drawable resource
fun getWeatherIconResource(iconName: String): Int {
    return when (iconName) {
        "01d" -> R.drawable.ic_weather_01d
        "01n" -> R.drawable.ic_weather_01n
        "02d" -> R.drawable.ic_weather_02d
        "02n" -> R.drawable.ic_weather_02n
        "03d", "03n" -> R.drawable.ic_weather_03d
        "04d", "04n" -> R.drawable.ic_weather_04d
        "09d", "09n" -> R.drawable.ic_weather_09d
        "10d" -> R.drawable.ic_weather_10d
        "10n" -> R.drawable.ic_weather_10n
        "11d", "11n" -> R.drawable.ic_weather_11d
        "13d", "13n" -> R.drawable.ic_weather_13d
        "50d", "50n" -> R.drawable.ic_weather_50d
        else -> R.drawable.ic_weather_01d // Default
    }
}
