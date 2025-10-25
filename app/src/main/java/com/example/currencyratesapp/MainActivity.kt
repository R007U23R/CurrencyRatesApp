package com.example.currencyratesapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    // Meaningful variable names (Requirement #1)
    private lateinit var txtBaseCurrency: TextView
    private lateinit var editTextFilter: EditText
    private lateinit var listViewCurrencies: ListView
    private lateinit var progressBar: ProgressBar
    private lateinit var txtError: TextView
    private lateinit var btnRefresh: Button

    // Data components
    private lateinit var dataLoader: DataLoader
    private lateinit var parser: Parser

    // Adapters and lists
    private lateinit var currencyAdapter: ArrayAdapter<String>
    private var allCurrencyRates = listOf<CurrencyRate>()
    private var filteredCurrencyRates = listOf<CurrencyRate>()

    // Coroutine scope for background operations
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        initializeViews()

        // Initialize data components
        dataLoader = DataLoader()
        parser = Parser()

        // Set up ListView adapter
        setupListView()

        // Set up filter/search functionality
        setupFilterListener()

        // Set up refresh button
        setupRefreshButton()

        // Load currency data
        loadCurrencyData()
    }

    /**
     * Initialize all view references
     */
    private fun initializeViews() {
        txtBaseCurrency = findViewById(R.id.txtBaseCurrency)
        editTextFilter = findViewById(R.id.editTextFilter)
        listViewCurrencies = findViewById(R.id.listViewCurrencies)
        progressBar = findViewById(R.id.progressBar)
        txtError = findViewById(R.id.txtError)
        btnRefresh = findViewById(R.id.btnRefresh)
    }

    /**
     * Set up ListView with adapter
     */
    private fun setupListView() {
        // Create adapter with empty list initially
        currencyAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            mutableListOf<String>()
        )
        listViewCurrencies.adapter = currencyAdapter

        // Add click listener to show currency details
        listViewCurrencies.setOnItemClickListener { _, _, position, _ ->
            val clickedCurrency = if (filteredCurrencyRates.isEmpty()) {
                if (position < allCurrencyRates.size) allCurrencyRates[position] else null
            } else {
                if (position < filteredCurrencyRates.size) filteredCurrencyRates[position] else null
            }

            clickedCurrency?.let {
                showCurrencyDetails(it)
            }
        }
    }

    /**
     * Set up filter/search functionality
     * Requirement: User should be able to filter currencies list
     */
    private fun setupFilterListener() {
        editTextFilter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filterCurrencies(s.toString())
            }
        })
    }

    /**
     * Set up refresh button click listener
     */
    private fun setupRefreshButton() {
        btnRefresh.setOnClickListener {
            loadCurrencyData()
        }
    }

    /**
     * Show detailed information about a currency
     */
    private fun showCurrencyDetails(currency: CurrencyRate) {
        // Create detailed message
        val details = StringBuilder()
        details.append("Currency: ${currency.currencyCode}\n")
        details.append("Name: ${currency.currencyName}\n")
        details.append("Exchange Rate: %.4f\n".format(currency.exchangeRate))
        details.append("\nThis means:\n")
        details.append("1 USD = %.4f ${currency.currencyCode}\n".format(currency.exchangeRate))
        details.append("1 ${currency.currencyCode} = %.4f USD".format(1.0 / currency.exchangeRate))

        // Show in an AlertDialog for better presentation
        android.app.AlertDialog.Builder(this)
            .setTitle(currency.currencyCode)
            .setMessage(details.toString())
            .setPositiveButton("OK", null)
            .show()
    }

    /**
     * Load currency data using DataLoader and Parser
     * Runs in background using coroutines
     */
    private fun loadCurrencyData() {
        // Show loading state
        showLoading(true)

        // Launch coroutine for background processing
        coroutineScope.launch {
            try {
                // Load data in background (DataLoader requirement)
                val jsonData = dataLoader.loadCurrencyData()

                if (jsonData != null) {
                    // Parse JSON data (Updated to use JSON)
                    allCurrencyRates = parser.parseJSONData(jsonData)

                    // Update UI with parsed data
                    updateCurrencyList(allCurrencyRates)
                    showLoading(false)
                } else {
                    // Show error if data loading failed
                    showError()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showError()
            }
        }
    }

    /**
     * Filter currencies based on user input
     */
    private fun filterCurrencies(filterText: String) {
        filteredCurrencyRates = if (filterText.isEmpty()) {
            // Show all currencies if filter is empty
            allCurrencyRates
        } else {
            // Filter by currency code
            allCurrencyRates.filter {
                it.currencyCode.contains(filterText.uppercase())
            }
        }

        // Update the displayed list
        updateCurrencyList(filteredCurrencyRates)
    }

    /**
     * Update the ListView with currency data
     * Format: "USD - 1.235" as per requirement
     */
    private fun updateCurrencyList(currencies: List<CurrencyRate>) {
        currencyAdapter.clear()

        // Add formatted currency strings to adapter
        currencies.forEach { currency ->
            currencyAdapter.add(currency.getDisplayFormat())
        }

        currencyAdapter.notifyDataSetChanged()
    }

    /**
     * Show or hide loading indicator
     */
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            listViewCurrencies.visibility = View.GONE
            txtError.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            listViewCurrencies.visibility = View.VISIBLE
            txtError.visibility = View.GONE
        }
    }

    /**
     * Show error message
     */
    private fun showError() {
        progressBar.visibility = View.GONE
        listViewCurrencies.visibility = View.GONE
        txtError.visibility = View.VISIBLE
    }

    /**
     * Clean up coroutines when activity is destroyed
     */
    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}