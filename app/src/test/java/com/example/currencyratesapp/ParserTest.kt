package com.example.currencyratesapp

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * Tests for Parser class
 * This verifies JSON parsing works correctly
 */
class ParserTest {

    // Create parser instance for all tests
    private lateinit var parser: Parser

    @Before
    fun setup() {
        // This runs before each test
        parser = Parser()
    }

    /**
     * Test parsing valid JSON data
     */
    @Test
    fun testParseValidJSON() {
        // Sample JSON data (like what the API returns)
        val testJson = """
        {
            "usd": {
                "eur": 0.9234,
                "gbp": 0.7890
            }
        }
        """

        // Parse the JSON
        val results = parser.parseJSONData(testJson)

        // Check we got 2 currencies
        assertEquals(2, results.size)

        // Check first currency is EUR (alphabetically sorted)
        assertEquals("EUR", results[0].currencyCode)
        assertEquals(0.9234, results[0].exchangeRate, 0.0001)
    }

    /**
     * Test with empty JSON
     */
    @Test
    fun testEmptyJSON() {
        val emptyJson = "{}"
        val results = parser.parseJSONData(emptyJson)

        // Should return empty list, not crash
        assertTrue(results.isEmpty())
    }

    /**
     * Test with invalid JSON
     */
    @Test
    fun testInvalidJSON() {
        val badJson = "This is not JSON at all"
        val results = parser.parseJSONData(badJson)

        // Should handle error gracefully
        assertTrue(results.isEmpty())
    }
}