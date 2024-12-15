package com.example.weather

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

// Define the dataStore property
val Context.dataStore by preferencesDataStore(name = "weather_preferences")
