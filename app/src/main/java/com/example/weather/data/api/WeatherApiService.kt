package com.example.weather.data.api

import com.example.weather.data.model.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Base URL for the OpenWeatherMap API
private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
// API Key for authentication with OpenWeatherMap
private const val API_KEY = "2a06b9804f9f69d2f5928e71922bce4e"
// Default units for temperature (metric for Celsius)
private const val UNITS = "metric"

interface WeatherApiService {
    /**
     * Fetch current weather data by city name.
     *
     * @param city The name of the city (e.g., "New York").
     * @param apiKey The API key for authentication. Default is [API_KEY].
     * @param units The unit system for temperature (metric, imperial, etc.). Default is "metric".
     * @return A [WeatherResponse] object containing weather data for the specified city.
     */
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = UNITS // For Celsius
    ): WeatherResponse

    /**
     * Fetch current weather data by geographic coordinates.
     *
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     * @param apiKey The API key for authentication. Default is [API_KEY].
     * @param units The unit system for temperature (metric, imperial, etc.). Default is "metric".
     * @return A [WeatherResponse] object containing weather data for the specified location.
     */
    @GET("weather")
    suspend fun getCurrentWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = UNITS
    ): WeatherResponse
}

/**
 * API request service
 */
object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }
}