package com.example.currencyratesapp

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit test for CurrencyRate data class
 * This tests your data model without running the actual app
 */
class CurrencyRateTest {

    /**
     * Test that we can create a CurrencyRate object correctly
     */
    @Test
    fun testCreateCurrencyRate() {
        // Create a test currency
        val currency = CurrencyRate(
            currencyCode = "EUR",
            currencyName = "Euro",
            exchangeRate = 0.9234
        )

        // Check all values are set correctly
        assertEquals("EUR", currency.currencyCode)
        assertEquals("Euro", currency.currencyName)
        assertEquals(0.9234, currency.exchangeRate, 0.0001)
    }

    /**
     * Test the display format method
     */
    @Test
    fun testDisplayFormat() {
        // Create a test currency
        val currency = CurrencyRate("GBP", "British Pound", 0.7890)

        // Get the display string
        val display = currency.getDisplayFormat()

        // Check it matches expected format
        assertEquals("GBP - 0.7890", display)
    }

    /**
     * Test with a large number
     */
    @Test
    fun testLargeNumber() {
        val currency = CurrencyRate("JPY", "Japanese Yen", 147.256789)
        val display = currency.getDisplayFormat()

        // Should round to 4 decimal places
        assertEquals("JPY - 147.2568", display)
    }
}