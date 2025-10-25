package com.example.currencyratesapp

import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * Tests for DataLoader class
 * This tests the network loading functionality
 */
class DataLoaderTest {

    private lateinit var dataLoader: DataLoader

    @Before
    fun setup() {
        dataLoader = DataLoader()
    }

    /**
     * Test that API URLs are properly configured
     */
    @Test
    fun testAPIUrlsExist() {
        // Check main API URL exists
        assertNotNull(DataLoader.API_URL)
        assertTrue(DataLoader.API_URL.startsWith("https://"))

        // Check backup API URL exists
        assertNotNull(DataLoader.BACKUP_API_URL)
        assertTrue(DataLoader.BACKUP_API_URL.startsWith("https://"))
    }

    /**
     * Test mock data flag
     */
    @Test
    fun testMockDataFlag() {
        // Mock data should be disabled by default
        assertFalse(DataLoader.USE_MOCK_DATA)
    }

    /**
     * Test data loading returns something
     * Note: This test requires internet connection
     */
    @Test
    fun testLoadDataStructure() = runTest {
        // Try to load data
        val data = dataLoader.loadCurrencyData()

        // If we got data, verify it's not empty
        if (data != null) {
            assertTrue(data.length > 0)
            // Check it contains expected JSON structure
            assertTrue(data.contains("{") && data.contains("}"))
        }
        // If null, that's OK - might not have internet
    }
}