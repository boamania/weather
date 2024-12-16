# Weather App

A basic weather application that allows users to search for weather information for any US city and retrieves weather data based on the user's location if permission is granted. 
The app uses the OpenWeatherMap API for fetching weather data.

---

## Features

### Core Features:
1. **Search Screen**:
   - Users can search for weather information by entering a US city.
   - Fetches data from the [OpenWeatherMap API](https://openweathermap.org/api).
   - Displays weather information, including a weather icon.

2. **Last City Auto-Load**:
   - Automatically loads the last searched city when the app is launched.

3. **Location-Based Weather**:
   - Prompts the user for location access upon launch.
   - If permission is granted, retrieves and displays weather data for the user's current location.

4. **Image Caching**:
   - Efficient caching of weather icons to reduce redundant downloads and improve performance.

---

## Project Structure

```plaintext
.
├── app
│   ├── src
│   │   ├── main
│   │   │   ├── java/com/example/weather
│   │   │   │   ├── data
│   │   │   │   │   ├── api
│   │   │   │   │   │   └── WeatherApiService.kt
│   │   │   │   │   ├── datastore
│   │   │   │   │   │   └── LastSearchExtensions.kt
│   │   │   │   │   │   └── LastSearchDataStore
│   │   │   │   ├── model
│   │   │   │   │   └── WeatherResponse.kt
│   │   │   │   ├── ui
│   │   │   │   │   ├── screen
│   │   │   │   │   │   └── WeatherScreen.kt
│   │   │   │   │   ├── theme
│   │   │   │   │   │   └── Color.kt 
│   │   │   │   │   │   └── Theme.kt 
│   │   │   │   │   │   └── Type.kt 
│   │   │   │   ├── util
│   │   │   │   │   └── LocationHelper.kt
│   │   │   │   └── viewmodel
│   │   │   │       └── WeatherViewModel.kt
│   │   │   │       └── WeatherViewModelFactory
│   │   │   └── MainActivity.kt
│   │   ├── res
│   │   │   ├── drawable
│   │   │   │   └── (Weather icons, e.g., ic_weather_01d.xml)
│   │   │   ├── values
│   │   │   │   ├── strings.xml
│   │   │   │   ├── colors.xml
│   │   │   │   └── themes.xml
│   │   │   ├── values-es
│   │   │   │   └── strings.xml (Spanish translations)
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
├── README.md
