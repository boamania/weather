package com.example.weather.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.utils.LocationHelper
import com.example.weather.R
import com.example.weather.data.datastore.LastSearchDataStore
import com.example.weather.data.api.WeatherApi
import com.example.weather.data.api.WeatherApiService
import com.example.weather.data.model.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val context: Context,
    private val weatherApiService: WeatherApiService = WeatherApi.retrofitService
) : ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> get() = _weatherState

    init {
        // Automatically fetch weather based on the user's current location on initialization.
        fetchWeatherForLocation()
    }

    // Fetch weather data based on the user's current location.
    fun fetchWeatherForLocation() {
        viewModelScope.launch {
            try {
                val locationHelper = LocationHelper(context)
                val location = locationHelper.getLastKnownLocation()
                location?.let {
                    // Make API call to fetch weather data using latitude and longitude.
                    val response = weatherApiService.getCurrentWeatherByCoordinates(it.latitude, it.longitude)
                    _weatherState.value = WeatherState(weatherData = response)
                } ?: run {
                    // Handle case where location is unavailable.
                    _weatherState.value = WeatherState(error = context.getString(R.string.unable_to_fetch_location))
                }
            } catch (e: Exception) {
                _weatherState.value = WeatherState(error = e.message)
            }
        }
    }

    // Fetch weather data for a specific city query.
    fun fetchWeather(query: String) {
        viewModelScope.launch {
            _weatherState.value = WeatherState(isLoading = true)
            try {
                // Make API call to fetch weather data for the specified city.
                val response = weatherApiService.getCurrentWeather(query)
                _weatherState.value = WeatherState(weatherData = response)
                saveLastSearch(query, response)
            } catch (e: Exception) {
                // Handle API call errors.
                _weatherState.value = WeatherState(error = e.message)
            }
        }
    }

    // Save the last search query and result in the data store.
    private suspend fun saveLastSearch(query: String, response: WeatherResponse) {
        val condition = response.weather.firstOrNull()?.description ?: context.getString(R.string.unknown)
        // Save query, temperature, and condition to the data store.
        LastSearchDataStore.saveLastSearch(context, query, response.main.temp.toString(), condition)
    }
}

// Data class representing the state of the weather UI.
data class WeatherState(
    val isLoading: Boolean = false, // Indicates whether the weather data is being fetched.
    val weatherData: WeatherResponse? = null, // Holds the fetched weather data.
    val error: String? = null // Holds error messages if any occur during the process.
)
