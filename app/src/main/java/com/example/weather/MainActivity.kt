package com.example.weather

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import android.Manifest
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.example.weather.ui.screen.WeatherScreen
import com.example.weather.ui.theme.WeatherTheme
import com.example.weather.viewmodel.WeatherViewModel
import com.example.weather.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Restart activity if permission is granted
                refreshActivity()
            } else {
                // Handle permission denial (optional)
                showPermissionDeniedMessage()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check for location permissions
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            // Permission already granted, proceed with the app
            setMainContent()
        }
    }

    private fun setMainContent() {
        setContent {
            WeatherTheme {
                WeatherSearchScreen()
            }
        }
    }

    private fun refreshActivity() {
        finish() // End the current instance
        startActivity(Intent(this, MainActivity::class.java)) // Start a new instance
    }

    private fun showPermissionDeniedMessage() {
        // You can show a Snackbar, Toast, or dialog here
        println("Permission Denied: Unable to access location")
    }
}

@Composable
fun WeatherSearchScreen() {
    val context = LocalContext.current.applicationContext
    val viewModel: WeatherViewModel = viewModel(factory = WeatherViewModelFactory(context))

    // Surface to define the UI
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        WeatherScreen(viewModel = viewModel)
    }
}
