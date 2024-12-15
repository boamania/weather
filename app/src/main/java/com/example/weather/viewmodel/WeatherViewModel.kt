package com.example.weather.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.LocationHelper
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
        fetchWeatherForLocation()
    }

    fun fetchWeatherForLocation() {
        viewModelScope.launch {
            try {
                val locationHelper = LocationHelper(context)
                val location = locationHelper.getLastKnownLocation()
                location?.let {
                    val response = weatherApiService.getCurrentWeatherByCoordinates(it.latitude, it.longitude)
                    _weatherState.value = WeatherState(weatherData = response)
                } ?: run {
                    _weatherState.value = WeatherState(error = "Unable to fetch location")
                }
            } catch (e: Exception) {
                _weatherState.value = WeatherState(error = e.message)
            }
        }
    }

    fun fetchWeather(query: String) {
        viewModelScope.launch {
            _weatherState.value = WeatherState(isLoading = true)
            try {
                val response = weatherApiService.getCurrentWeather(query)
                _weatherState.value = WeatherState(weatherData = response)
                saveLastSearch(query, response)
            } catch (e: Exception) {
                _weatherState.value = WeatherState(error = e.message)
            }
        }
    }

    private suspend fun saveLastSearch(query: String, response: WeatherResponse) {
        val condition = response.weather.firstOrNull()?.description ?: "Unknown"
        LastSearchDataStore.saveLastSearch(context, query, response.main.temp.toString(), condition)
    }
}

data class WeatherState(
    val isLoading: Boolean = false,
    val weatherData: WeatherResponse? = null,
    val error: String? = null
)
