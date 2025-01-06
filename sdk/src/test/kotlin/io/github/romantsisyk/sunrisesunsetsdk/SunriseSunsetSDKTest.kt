package io.github.romantsisyk.sunrisesunsetsdk

import io.github.romantsisyk.sunrisesunsetsdk.exceptions.InvalidDateException
import io.github.romantsisyk.sunrisesunsetsdk.exceptions.InvalidRequestException
import io.github.romantsisyk.sunrisesunsetsdk.exceptions.InvalidTimeZoneException
import io.github.romantsisyk.sunrisesunsetsdk.exceptions.SunriseSunsetException
import io.github.romantsisyk.sunrisesunsetsdk.models.SuccessApiResponse
import io.github.romantsisyk.sunrisesunsetsdk.network.NetworkClient
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SunriseSunsetSDKTest {

    private lateinit var networkClient: NetworkClient
    private lateinit var sdk: SunriseSunsetSDK

    private val baseUrl = "https://api.sunrise-sunset.org/json"
    private val sampleRealResponse = """
        {
          "results": {
            "sunrise": "5:29:40 AM",
            "sunset": "5:28:02 PM",
            "solar_noon": "11:28:51 AM",
            "day_length": "11:58:22",
            "civil_twilight_begin": "5:08:56 AM",
            "civil_twilight_end": "5:48:46 PM",
            "nautical_twilight_begin": "4:43:33 AM",
            "nautical_twilight_end": "6:14:09 PM",
            "astronomical_twilight_begin": "4:18:11 AM",
            "astronomical_twilight_end": "6:39:31 PM"
          },
          "status": "OK" ,
          "tzid": "UTC"
        }
    """.trimIndent()

    @BeforeEach
    fun setUp() {
        networkClient = mockk()
        sdk = SunriseSunsetSDK.builder()
            .setNetworkClient(networkClient)
            .build()
    }

    private fun mockResponse(url: String, response: String) {
        every { networkClient.get(url) } returns response
    }

    @Test
    fun `should return SuccessApiResponse on successful real request`() {
        val lat = 4.0
        val lng = 4.0
        val url = "$baseUrl?lat=$lat&lng=$lng"

        mockResponse(url, sampleRealResponse)

        val response = sdk.getSunTimes(lat, lng)

        assertTrue(response is SuccessApiResponse)
        val successResponse = response as SuccessApiResponse

        assertEquals("OK", successResponse.status)
        assertEquals("UTC", successResponse.tzid)
        assertEquals("5:29:40 AM", successResponse.results.sunrise)
        assertEquals("5:28:02 PM", successResponse.results.sunset)
        assertEquals("11:28:51 AM", successResponse.results.solarNoon)
    }


    @Test
    fun `should throw InvalidRequestException on INVALID_REQUEST status`() {
        val lat = 4.0
        val lng = 4.0
        val url = "$baseUrl?lat=$lat&lng=$lng"
        val sampleInvalidRequestResponse = """
            {
              "results": "",
              "status": ${SunriseSunsetSDK.Companion.STATUS_INVALID_REQUEST}
            }
        """.trimIndent()

        mockResponse(url, sampleInvalidRequestResponse)

        assertThrows(InvalidRequestException::class.java) {
            sdk.getSunTimes(lat, lng)
        }
    }

    @Test
    fun `should throw InvalidDateException on INVALID_DATE status`() {
        val lat = 4.0
        val lng = 4.0
        val invalidDate = "invalid-date"
        val url = "$baseUrl?lat=$lat&lng=$lng&date=$invalidDate"
        val sampleInvalidDateResponse = """
        {
          "results": "",
          "status": ${SunriseSunsetSDK.Companion.STATUS_INVALID_DATE}
        }
    """.trimIndent()

        mockResponse(url, sampleInvalidDateResponse)

        assertThrows(InvalidDateException::class.java) {
            sdk.getSunTimes(lat, lng, date = invalidDate)
        }
    }

    @Test
    fun `should throw InvalidTimeZoneException on INVALID_TZID status`() {
        val lat = 4.0
        val lng = 4.0
        val invalidDate = "invalid-date"
        val url = "$baseUrl?lat=$lat&lng=$lng&date=$invalidDate"
        val sampleInvalidDateResponse = """
        {
          "results": "",
          "status": ${SunriseSunsetSDK.Companion.STATUS_INVALID_TZID}
        }
    """.trimIndent()

        mockResponse(url, sampleInvalidDateResponse)

        assertThrows(InvalidTimeZoneException::class.java) {
            sdk.getSunTimes(lat, lng, date = invalidDate)
        }
    }

    @Test
    fun `should throw STATUS_UNKNOWN_ERROR on UNKNOWN_ERROR status`() {
        val lat = 4.0
        val lng = 4.0
        val invalidDate = "invalid-date"
        val url = "$baseUrl?lat=$lat&lng=$lng&date=$invalidDate"
        val sampleInvalidDateResponse = """
        {
          "results": "",
          "status": ${SunriseSunsetSDK.Companion.STATUS_UNKNOWN_ERROR}
        }
    """.trimIndent()

        mockResponse(url, sampleInvalidDateResponse)

        assertThrows(SunriseSunsetException::class.java) {
            sdk.getSunTimes(lat, lng, date = invalidDate)
        }
    }

    @Test
    fun `should throw SunriseSunsetException on INVALID_DATE status`() {
        val lat = 4.0
        val lng = 4.0
        val invalidDate = "invalid-date"
        val url = "$baseUrl?lat=$lat&lng=$lng&date=$invalidDate"
        val sampleInvalidDateResponse = """
        {
          "results": "",
          "status": "Ops. I did it again"
        }
    """.trimIndent()

        mockResponse(url, sampleInvalidDateResponse)

        assertThrows(SunriseSunsetException::class.java) {
            sdk.getSunTimes(lat, lng, date = invalidDate)
        }
    }

    @Test
    fun `should return SuccessApiResponse on successful async real request`() = runBlocking {
        val lat = 4.0
        val lng = 4.0
        val url = "$baseUrl?lat=$lat&lng=$lng"

        mockResponse(url, sampleRealResponse)

        val response = sdk.getSunTimesAsync(lat, lng)

        assertTrue(response is SuccessApiResponse)
        val successResponse = response as SuccessApiResponse

        assertEquals("OK", successResponse.status)
        assertEquals("UTC", successResponse.tzid)
        assertEquals("5:29:40 AM", successResponse.results.sunrise)
        assertEquals("5:28:02 PM", successResponse.results.sunset)
        assertEquals("11:28:51 AM", successResponse.results.solarNoon)
    }

    @Test
    fun `should throw SunriseSunsetException on missing status field`() {
        val lat = 4.0
        val lng = 4.0
        val url = "$baseUrl?lat=$lat&lng=$lng"
        val sampleMissingStatusResponse = """{ "results": {} }""".trimIndent()

        mockResponse(url, sampleMissingStatusResponse)

        assertThrows(SunriseSunsetException::class.java) {
            sdk.getSunTimes(lat, lng)
        }
    }

    @Test
    fun `should throw SunriseSunsetException on malformed JSON`() {
        val lat = 4.0
        val lng = 4.0
        val url = "$baseUrl?lat=$lat&lng=$lng"
        val sampleMalformedJson = """{ "results": }""" // Malformed JSON

        mockResponse(url, sampleMalformedJson)

        assertThrows(SunriseSunsetException::class.java) {
            sdk.getSunTimes(lat, lng)
        }
    }

    @Test
    fun `should handle SunriseSunsetException correctly`() {
        val lat = 4.0
        val lng = 4.0
        val url = "$baseUrl?lat=$lat&lng=$lng"
        val sampleUnknownErrorResponse = """
        {
            "results": "",
            "status": "${SunriseSunsetSDK.STATUS_UNKNOWN_ERROR}"
        }
    """.trimIndent()

        mockResponse(url, sampleUnknownErrorResponse)

        assertThrows(SunriseSunsetException::class.java) {
            sdk.getSunTimes(lat, lng)
        }
    }
}
