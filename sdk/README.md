![release](https://img.shields.io/badge/release-v1.0.0-blue)
![platform](https://img.shields.io/badge/platform-JVM%20|%20Kotlin-lightgrey)
![kotlin](https://img.shields.io/badge/Kotlin-2.0.0-orange)
![jvm](https://img.shields.io/badge/JVM-17-green)
![build](https://img.shields.io/badge/Build%20System-Gradle-lightgrey)
![license](https://img.shields.io/badge/license-MIT-brightgreen)

 # **Sunrise-Sunset SDK for Kotlin**

A Kotlin SDK to interact with the [Sunrise-Sunset API](https://sunrise-sunset.org/api), providing accurate sunrise and sunset times based on geographic coordinates. The SDK allows developers to easily retrieve sunrise and sunset times, solar noon, day length, and twilight information for any given location and date.

---

## **Note**

This SDK is currently a **test project** designed to explore and understand the complete lifecycle of creating, testing, and publishing a library. While the SDK is fully functional and adheres to best practices, it is not yet published to a public repository such as Maven Central or JitPack.

**Plans for the Future:**
- The SDK will be officially published.
- Long-term support is not guaranteed at this stage but may be considered depending on usage.
- The Sunrise-Sunset API itself is stable, but the SDK does not anticipate or account for potential API changes or the addition of new functionality.

This is primarily a **learning and experimentation project** to understand:
1. Creating a reusable SDK.
2. Testing all aspects of the development lifecycle.
3. Exploring the process of publishing a library for public use.

---

## **Features**

- **Simple API Integration:** Fetch sunrise and sunset times with minimal setup.
- **Type-safe Timezone Support:** Includes a time zone identifier enum to ensure accurate time zone formatting.
- **Synchronous and Asynchronous Calls:** Fetch sun times both synchronously and asynchronously using Kotlin coroutines.
- **Flexible Date Input:** Supports various date formats, including relative dates (e.g., `today`, `tomorrow`).
- **Timezone Support:** Allows specifying a time zone for the returned results (e.g., `Europe/London`).
- **Error Handling:** Handles various API errors gracefully with descriptive exceptions.

---

## Test Coverage Summary

The project has been extensively tested to ensure reliability and robustness.

| **Component**                | **Class (%)** | **Method (%)** | **Line (%)** | **Branch (%)** |
|-------------------------------|---------------|----------------|--------------|----------------|
| **Overall Coverage**          | **100%**      | **97%**        | **98%**      | **75%**        |
| **Exceptions**                | 100%          | 100%           | 100%         | 100%           |
| **Models**                    | 100%          | 100%           | 100%         | 50%            |
| **Network**                   | 100%          | 100%           | 100%         | 83%            |
| **Utils**                     | 100%          | 100%           | 100%         | 100%           |
| **SDK Core (SDK & Builder)**  | 100%          | 92%            | 89%          | 91%            |

---

## **Installation**

**This project is not yet published** to any public repository. Once published, the installation process will be as follows:

### **Using Gradle (JitPack)**

1. **Add JitPack repository to your `build.gradle.kts` file:**

   ```kotlin
   repositories {
       mavenCentral()
       maven("https://jitpack.io")
   }
   ```

2. **Add the SDK dependency:**

   ```kotlin
   dependencies {
       implementation("io.github.romantsisyk:sunrisesunsetsdk:v1.0.0")
   }
   ```

3. **Sync your project** to download and integrate the SDK.

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

You can use both synchronous and asynchronous methods to fetch the sun times.

#### **Synchronous Example:**

```kotlin
val lat = 36.7201600   // Latitude of your location
val lng = -4.4203400   // Longitude of your location

try {
    val response = sdk.getSunTimes(
        lat = lat,
        lng = lng,
        date = "2024-11-16",
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

The project follows a simple and modular structure to keep the code organized and maintainable:

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

1. **Future API Changes:** The SDK is built for the current version of the Sunrise-Sunset API and does not predict future changes or new features.
2. **Lack of Offline Support:** The SDK does not include offline caching at this time.
3. **No Localization:** The SDK currently supports English-only error messages and responses.

---

## **Future Plans**

- **Publication:** The SDK may be published to **JitPack** or **Maven Central** as a test case to explore the publishing process.
- **Feature Enhancements:** Additional functionality, such as caching, localization, and weather integration, may be added based on feedback and demand.
- **Support:** While the SDK is not guaranteed long-term support, it will likely be updated if the need arises or the API changes.

---

## **Contributing**

This project is open for contributions, especially from developers who want to explore SDK development or propose improvements.

---

## **License**

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
