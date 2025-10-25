package com.example.currencyratesapp

import org.json.JSONObject

/**
 * Parser class for parsing JSON data
 * Updated to handle JSON since most free APIs use JSON format
 */
class Parser {

    /**
     * Parse JSON data from the API
     * Handles multiple JSON formats for flexibility
     */
    fun parseJSONData(jsonString: String): List<CurrencyRate> {
        val currencyList = mutableListOf<CurrencyRate>()

        try {
            val jsonObject = JSONObject(jsonString)

            // Check which API format we're dealing with
            when {
                // Format 1: @fawazahmed0/currency-api format
                jsonObject.has("usd") -> {
                    val rates = jsonObject.getJSONObject("usd")
                    val keys = rates.keys()

                    while (keys.hasNext()) {
                        val currencyCode = keys.next()
                        val rate = rates.getDouble(currencyCode)

                        // Skip if it's the base currency itself
                        if (currencyCode.uppercase() != "USD") {
                            currencyList.add(
                                CurrencyRate(
                                    currencyCode = currencyCode.uppercase(),
                                    currencyName = getCurrencyName(currencyCode.uppercase()),
                                    exchangeRate = rate
                                )
                            )
                        }
                    }
                }

                // Format 2: open.er-api.com format
                jsonObject.has("rates") -> {
                    val rates = jsonObject.getJSONObject("rates")
                    val keys = rates.keys()

                    while (keys.hasNext()) {
                        val currencyCode = keys.next()
                        val rate = rates.getDouble(currencyCode)

                        // Skip USD since it's the base
                        if (currencyCode != "USD") {
                            currencyList.add(
                                CurrencyRate(
                                    currencyCode = currencyCode,
                                    currencyName = getCurrencyName(currencyCode),
                                    exchangeRate = rate
                                )
                            )
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Sort alphabetically by currency code
        return currencyList.sortedBy { it.currencyCode }
    }

    /**
     * Old XML parsing method (kept for compatibility)
     */
    fun parseXMLData(xmlString: String): List<CurrencyRate> {
        // Return empty list since we're using JSON now
        return emptyList()
    }

    /**
     * Get full currency name from code
     */
    private fun getCurrencyName(code: String): String {
        return when (code) {
            "EUR" -> "Euro"
            "GBP" -> "British Pound"
            "JPY" -> "Japanese Yen"
            "AUD" -> "Australian Dollar"
            "CAD" -> "Canadian Dollar"
            "CHF" -> "Swiss Franc"
            "CNY" -> "Chinese Yuan"
            "INR" -> "Indian Rupee"
            "NZD" -> "New Zealand Dollar"
            "MXN" -> "Mexican Peso"
            "SGD" -> "Singapore Dollar"
            "HKD" -> "Hong Kong Dollar"
            "NOK" -> "Norwegian Krone"
            "SEK" -> "Swedish Krona"
            "RUB" -> "Russian Ruble"
            "ZAR" -> "South African Rand"
            "BRL" -> "Brazilian Real"
            "KRW" -> "South Korean Won"
            "THB" -> "Thai Baht"
            "MYR" -> "Malaysian Ringgit"
            "PHP" -> "Philippine Peso"
            "IDR" -> "Indonesian Rupiah"
            "TRY" -> "Turkish Lira"
            "AED" -> "UAE Dirham"
            "SAR" -> "Saudi Riyal"
            else -> code
        }
    }
}