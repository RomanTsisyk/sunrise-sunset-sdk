
---

![release](https://img.shields.io/badge/release-v1.0.0-blue)  ![platform](https://img.shields.io/badge/platform-JVM%20|%20Kotlin-lightgrey)  ![kotlin](https://img.shields.io/badge/Kotlin-2.0.0-orange)  ![jvm](https://img.shields.io/badge/JVM-17-green)  ![build](https://img.shields.io/badge/Build%20System-Gradle-lightgrey)  ![license](https://img.shields.io/badge/license-MIT-brightgreen)  

# **Sunrise-Sunset SDK for Kotlin**

A Kotlin SDK to interact with the [Sunrise-Sunset API](https://sunrise-sunset.org/api), providing accurate sunrise and sunset times based on geographic coordinates. The SDK allows developers to easily retrieve sunrise and sunset times, solar noon, day length, and twilight information for any given location and date.

---

## **Note**

This SDK is currently a **test project** designed to explore and understand the complete lifecycle of creating, testing, and publishing a library. While the SDK is fully functional and adheres to best practices, it will not be officially published to a public repository (e.g., Maven Central, JitPack).

### **Repository Status**
- This repository will be **publicly archived** as a reference project.
- No future updates or support are guaranteed.
- The purpose of this project is to showcase my ability to create and work with SDKs.

---

## **Features**

- **Simple API Integration:** Fetch sunrise and sunset times with minimal setup.
- **Type-safe Timezone Support:** Includes a time zone identifier enum to ensure accurate time zone formatting.
- **Synchronous and Asynchronous Calls:** Fetch sun times both synchronously and asynchronously using Kotlin coroutines.
- **Flexible Date Input:** Supports various date formats, including relative dates (e.g., `today`, `tomorrow`).
- **Timezone Support:** Allows specifying a time zone for the returned results (e.g., `Europe/London`).
- **Error Handling:** Handles various API errors gracefully with descriptive exceptions.

---

## **Demo App**

This repository includes a `demo-app` folder that demonstrates the usage of the manually compiled `sunrise-sunset-sdk-1.0.0` library. The demo app showcases the functionality of the SDK and how it can be integrated into Android and Wear OS applications.

### **Demo App Highlights:**
- Provides a working example of how to integrate the SDK.
- Demonstrates both synchronous and asynchronous calls to fetch sunrise and sunset times.
- Includes a Wear OS implementation to explore its flexibility.

### **Important Notes**:
- The `sunrise-sunset-sdk-1.0.0` used in the demo app was manually compiled as part of this project.
- **This SDK will not be published** to any public repository.
- This project serves as a test to evaluate my skills in building and integrating an SDK.

---

## **Usage**

### **Initializing the SDK**

To begin using the SDK, first initialize the `SunriseSunsetSDK` instance. The SDK can be configured using a builder for greater flexibility.

```kotlin
import io.github.romantsisyk.sunrisesunsetsdk.SunriseSunsetSDK
import io.github.romantsisyk.sunrisesunsetsdk.utils.TimeZoneID

val sdk = SunriseSunsetSDK.builder().build()
```

### **Fetching Sunrise and Sunset Times**

#### **Synchronous Example:**

```kotlin
val lat = 36.7201600   // Latitude of your location
val lng = -4.4203400   // Longitude of your location

try {
    val response = sdk.getSunTimes(
        lat = lat,
        lng = lng,
        date = "today", // Fetch for today's date
        formatted = 0, // Use 0 to get ISO 8601 formatted times
        tzid = TimeZoneID.EUROPE_LONDON // Optional: Specify a timezone (e.g., Europe/London)
    )
    if (response.status == "OK") {
        println("Sunrise: ${response.results.sunrise}")
        println("Sunset: ${response.results.sunset}")
        println("Solar Noon: ${response.results.solarNoon}")
        println("Day Length: ${response.results.dayLength}")
    }
} catch (e: Exception) {
    println("Error: ${e.message}")
}
```

#### **Asynchronous Example (Coroutines):**

```kotlin
import kotlinx.coroutines.runBlocking

runBlocking {
    try {
        val response = sdk.getSunTimesAsync(
            lat = 36.7201600,
            lng = -4.4203400,
            date = "today",
            formatted = 0,
            tzid = TimeZoneID.EUROPE_LONDON
        )

        if (response.status == "OK") {
            println("Sunrise: ${response.results.sunrise}")
            println("Sunset: ${response.results.sunset}")
            println("Solar Noon: ${response.results.solarNoon}")
            println("Day Length: ${response.results.dayLength}")
        }
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}
```

---

## **Project Structure**

The project follows a modular structure for better organization and maintainability:

```
io.github.romantsisyk.sunrisesunsetsdk
│
├── config
│   └── SdkConfig.kt            # SDK configuration settings
│
├── exceptions
│   ├── SunriseSunsetException.kt
│   ├── InvalidRequestException.kt
│   ├── InvalidDateException.kt
│   └── InvalidTimeZoneException.kt
│
├── models
│   ├── ApiResponse.kt           # Data model for the API response
│   └── SunTimes.kt              # Data model for sun times (sunrise, sunset, etc.)
│
├── network
│   ├── JsonParser.kt            # JSON parsing utilities
│   └── NetworkClient.kt         # HTTP client for making API requests
│
├── utils
│   └── TimeZoneID.kt            # Enum for time zone identifiers
│
├── SunriseSunsetSDK.kt         # Main SDK class for API interactions
└── SunriseSunsetSdkBuilder.kt  # Builder for constructing the SDK instance
```

---

## **Known Limitations**

1. **Future API Changes**: The SDK is built for the current version of the Sunrise-Sunset API.
2. **No Offline Support**: This SDK does not include offline caching.
3. **Localization**: Currently, the SDK supports English-only error messages and responses.

---

## **Future Plans**

- **Public Archive**: This repository will remain publicly accessible for reference.
- **Feature Enhancements**: If further interest arises, caching and other features may be explored.

---

## **Contributing**

Contributions are welcome for developers interested in SDK development or proposing improvements.

---

## **License**

This project is licensed under the MIT License - see the [LICENSE](LICENSE.txt) file for details.

---

### **Key Updates**
- Added a clear note about **public archiving**.
- Expanded the demo app section with more details.
- Adjusted code examples for clarity.

Let me know if you’d like further refinements! 😊
