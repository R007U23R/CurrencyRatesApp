package com.example.currencyratesapp

/**
 * Data class representing a currency exchange rate
 * Requirement: Meaningful variable names
 */
data class CurrencyRate(
    val currencyCode: String,    // e.g., "EUR"
    val currencyName: String,    // e.g., "Euro"
    val exchangeRate: Double     // e.g., 0.9234
) {
    /**
     * Format for display in ListView
     * Shows as "EUR - 0.9234"
     */
    fun getDisplayFormat(): String {
        return "$currencyCode - %.4f".format(exchangeRate)
    }
}