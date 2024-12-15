package com.example.weather.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.weather.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object LastSearchDataStore {
    private val LAST_SEARCH_QUERY_KEY = stringPreferencesKey("last_search_query")
    private val LAST_SEARCH_RESULT_KEY = stringPreferencesKey("last_search_result")
    private val LAST_SEARCH_CONDITION_KEY =
        stringPreferencesKey("last_search_condition") // Key for condition

    fun getLastSearchQuery(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[LAST_SEARCH_QUERY_KEY]
        }
    }

    fun getLastSearchResult(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[LAST_SEARCH_RESULT_KEY]
        }
    }

    fun getLastSearchCondition(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[LAST_SEARCH_CONDITION_KEY]
        }
    }

    suspend fun saveLastSearch(context: Context, query: String, result: String, condition: String) {
        context.dataStore.edit { preferences ->
            preferences[LAST_SEARCH_QUERY_KEY] = query
            preferences[LAST_SEARCH_RESULT_KEY] = result
            preferences[LAST_SEARCH_CONDITION_KEY] = condition
        }
    }
}