package com.example.currencyratesapp

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI tests for MainActivity
 * These tests actually run the app on a device/emulator
 */
@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    // This rule starts the MainActivity before each test
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * Test that all UI elements are visible
     */
    @Test
    fun testUIElementsAreVisible() {
        // Check header is visible
        onView(withId(R.id.txtBaseCurrency))
            .check(matches(isDisplayed()))

        // Check filter input is visible
        onView(withId(R.id.editTextFilter))
            .check(matches(isDisplayed()))

        // Check refresh button is visible
        onView(withId(R.id.btnRefresh))
            .check(matches(isDisplayed()))
    }

    /**
     * Test typing in the filter field
     */
    @Test
    fun testTypingInFilter() {
        // Type "EUR" in the filter field
        onView(withId(R.id.editTextFilter))
            .perform(typeText("EUR"))

        // Close the keyboard
        onView(withId(R.id.editTextFilter))
            .perform(closeSoftKeyboard())

        // Check the text was entered
        onView(withId(R.id.editTextFilter))
            .check(matches(withText("EUR")))
    }

    /**
     * Test clicking refresh button
     */
    @Test
    fun testRefreshButton() {
        // Click the refresh button
        onView(withId(R.id.btnRefresh))
            .perform(click())

        // Check button is still there (app didn't crash)
        onView(withId(R.id.btnRefresh))
            .check(matches(isDisplayed()))
    }

    /**
     * Test clearing the filter
     */
    @Test
    fun testClearFilter() {
        // Type something
        onView(withId(R.id.editTextFilter))
            .perform(typeText("EUR"))

        // Clear it
        onView(withId(R.id.editTextFilter))
            .perform(clearText())

        // Check it's empty
        onView(withId(R.id.editTextFilter))
            .check(matches(withText("")))
    }
}