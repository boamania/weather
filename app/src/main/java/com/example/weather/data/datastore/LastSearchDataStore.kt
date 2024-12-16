package com.example.weather.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

object LastSearchDataStore {
    private val LAST_SEARCH_QUERY_KEY = stringPreferencesKey("last_search_query")
    private val LAST_SEARCH_RESULT_KEY = stringPreferencesKey("last_search_result")
    private val LAST_SEARCH_CONDITION_KEY =
        stringPreferencesKey("last_search_condition") // Key for condition

    /**
     * Save the last searched weather data to the DataStore.
     *
     * @param context The application context used to access the DataStore.
     * @param query The city name or query string for the last search.
     * @param result The temperature result from the last search.
     * @param condition The weather condition from the last search (e.g., "Clear sky").
     */
    suspend fun saveLastSearch(context: Context, query: String, result: String, condition: String) {
        context.dataStore.edit { preferences ->
            preferences[LAST_SEARCH_QUERY_KEY] = query
            preferences[LAST_SEARCH_RESULT_KEY] = result
            preferences[LAST_SEARCH_CONDITION_KEY] = condition
        }
    }
}