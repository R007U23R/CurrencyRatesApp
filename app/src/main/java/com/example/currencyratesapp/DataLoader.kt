package com.example.currencyratesapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/**
 * DataLoader class for downloading data in the background
 * Requirement: Background processing class
 * Using Coroutines instead of deprecated AsyncTask
 */
class DataLoader {

    // HTTP client for network requests
    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    // Using a free API that works without authentication
    companion object {
        // This API provides JSON data for free without authentication
        const val API_URL = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/usd.json"

        // Alternative backup API (also free)
        const val BACKUP_API_URL = "https://open.er-api.com/v6/latest/USD"
    }

    /**
     * Load currency data from the API
     * Returns JSON string since most modern APIs use JSON
     */
    suspend fun loadCurrencyData(): String? {
        return withContext(Dispatchers.IO) {
            try {
                // Try primary API first
                val request = Request.Builder()
                    .url(API_URL)
                    .build()

                val response = httpClient.newCall(request).execute()

                if (response.isSuccessful) {
                    response.body?.string()
                } else {
                    // Try backup API if primary fails
                    tryBackupAPI()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Try backup API if primary fails
                tryBackupAPI()
            }
        }
    }

    /**
     * Try backup API if primary fails
     */
    private fun tryBackupAPI(): String? {
        return try {
            val request = Request.Builder()
                .url(BACKUP_API_URL)
                .build()

            val response = httpClient.newCall(request).execute()

            if (response.isSuccessful) {
                response.body?.string()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}