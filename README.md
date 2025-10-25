# **Currency Exchange Rates App**

## **Project Overview**
A real-time currency exchange rates application that fetches and displays current exchange rates against USD. Built as Lab #5 for Mobile Application Development course.

## **Features**
- Live currency rates from web API
- Real-time filtering by currency code
- Click for detailed exchange information
- Background data loading with coroutines
- Error handling with user feedback
- Refresh capability

## **Technical Architecture**

### **Classes Structure**
- **MainActivity** - UI management and user interaction
- **DataLoader** - Background API data fetching
- **Parser** - JSON/XML data parsing
- **CurrencyRate** - Data model for currency information

### **Technologies Used**
- **Language:** Kotlin
- **Networking:** OkHttp3
- **Async Processing:** Kotlin Coroutines
- **Data Parsing:** JSON (org.json), XML (DOM)
- **UI Components:** ListView, EditText

## **Implementation Details**

### **API Integration**
Primary API: `https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/usd.json`
Backup API: `https://open.er-api.com/v6/latest/USD`

### **Key Components**
```
DataFlow: API → DataLoader → Parser → MainActivity → ListView
```

## **Challenges & Solutions**

### **Challenge 1: API Availability**
- **Issue:** Original FloatRates XML API was unreliable
- **Solution:** Implemented fallback to JSON APIs with multiple sources

### **Challenge 2: Background Processing**
- **Issue:** AsyncTask deprecated in API 30
- **Solution:** Migrated to Kotlin Coroutines for modern async handling

### **Challenge 3: ListView Scrolling**
- **Issue:** Scrolling issues in emulator
- **Solution:** Optimized layout weights and scrollbar properties

## **Testing**

### **Functionality Tests**
- ✅ Data loads from API
- ✅ Filter by currency code (EUR, GBP, etc.)
- ✅ Click for detailed conversion rates
- ✅ Refresh button reloads data
- ✅ Error message on network failure

### **Edge Cases**
- No internet connection → Shows error message
- API failure → Attempts backup API
- Empty filter → Shows all currencies

## **Learning Outcomes**

1. **Network Programming** - HTTP requests, API consumption
2. **Asynchronous Processing** - UI thread vs background operations
3. **Data Parsing** - Converting JSON/XML to objects
4. **Error Handling** - Graceful failure management
5. **Real-world Development** - Dealing with unreliable external dependencies

## **Setup Instructions**

1. Clone repository
2. Add dependencies in `build.gradle.kts`:
   ```kotlin
   implementation("com.squareup.okhttp3:okhttp:4.12.0")
   implementation("com.google.code.gson:gson:2.10.1")
   implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
   ```
3. Add Internet permission in `AndroidManifest.xml`
4. Build and run

## **Requirements Met**

- ✅ Meaningful variable names
- ✅ Java/Kotlin coding standards
- ✅ MainActivity with ListView display
- ✅ DataLoader for background processing
- ✅ Parser for data parsing
- ✅ Currency filtering capability
- ✅ Format: "USD - 1.235"

## **Conclusion**

This lab successfully demonstrates fundamental Android development concepts including network operations, background processing, and data parsing. The transition from XML to JSON APIs reflects real-world development where adaptability to changing external dependencies is crucial. The implementation follows SOLID principles with clear separation of concerns between data loading, parsing, and presentation layers.

---
