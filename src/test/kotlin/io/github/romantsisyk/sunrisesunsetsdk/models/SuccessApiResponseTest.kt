package io.github.romantsisyk.sunrisesunsetsdk.models

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ApiResponseTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `should serialize and deserialize SuccessApiResponse correctly`() {
        val response = SuccessApiResponse(
            results = SunTimes(
                sunrise = "06:00:00",
                sunset = "18:00:00",
                solarNoon = "12:00:00",
                dayLength = "12:00:00",
                civilTwilightBegin = "05:30:00",
                civilTwilightEnd = "18:30:00",
                nauticalTwilightBegin = "05:00:00",
                nauticalTwilightEnd = "19:00:00",
                astronomicalTwilightBegin = "04:30:00",
                astronomicalTwilightEnd = "19:30:00"
            ),
            status = "OK",
            tzid = "America/New_York"
        )
        val serialized = json.encodeToString(ApiResponse.serializer(), response)
        val deserialized = json.decodeFromString(ApiResponse.serializer(), serialized)
        assertTrue(deserialized is SuccessApiResponse)
        assertEquals(response, deserialized)
    }

    @Test
    fun `should serialize and deserialize ErrorApiResponse correctly`() {
        val response = ErrorApiResponse(results = "Error details", status = "ERROR")
        val serialized = json.encodeToString(ApiResponse.serializer(), response)
        val deserialized = json.decodeFromString(ApiResponse.serializer(), serialized)
        assertTrue(deserialized is ErrorApiResponse)
        assertEquals(response, deserialized)
    }
}