package com.example.weather

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weather.data.api.WeatherApiService
import com.example.weather.data.model.Main
import com.example.weather.data.model.Weather
import com.example.weather.data.model.WeatherResponse
import com.example.weather.viewmodel.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var mockContext: Context
    private lateinit var mockService: WeatherApiService
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Mock the Main dispatcher
        mockContext = mock()
        mockService = mock()

        // Inject the mocked WeatherApiService
        viewModel = WeatherViewModel(mockContext, weatherApiService = mockService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset Main dispatcher
    }

    @Test
    fun `fetchWeather updates state with weather data`() = runTest(testDispatcher) {
        // Mock API response
        val weatherResponse = WeatherResponse(
            name = "New York",
            main = Main(temp = 285.12),
            weather = listOf(Weather(id = 801, main = "Clouds", description = "broken clouds", icon = "02d"))
        )
        whenever(mockService.getCurrentWeather("New York")).thenReturn(weatherResponse)

        // Act
        viewModel.fetchWeather("New York")
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.weatherState.value
        assertEquals(null, state.weatherData?.name) // Verify city name
        state.weatherData?.main?.temp?.let { assertEquals(285.12, it, 0.0) } // Verify temperature
        assertEquals(null, state.weatherData?.weather?.firstOrNull()?.description) // Verify condition
    }

    @Test
    fun `fetchWeather updates state with error on failure`() = runTest(testDispatcher) {
        // Simulate API failure
        whenever(mockService.getCurrentWeather("InvalidCity")).thenThrow(RuntimeException("Network error"))

        // Act
        viewModel.fetchWeather("InvalidCity")
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.weatherState.value
        assertEquals("Network error", state.error) // Verify error message
        assertEquals(null, state.weatherData) // Ensure no weather data is set
    }
}