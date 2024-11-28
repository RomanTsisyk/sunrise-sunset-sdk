package io.github.romantsisyk.sunrisesunsetsdk.models

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SunTimesTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `should serialize and deserialize SunTimes correctly`() {
        val sunTimes = SunTimes(
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
        )
        val serialized = json.encodeToString(SunTimes.serializer(), sunTimes)
        val deserialized = json.decodeFromString(SunTimes.serializer(), serialized)
        assertEquals(sunTimes, deserialized)
    }

    @Test
    fun `should have correct field values`() {
        val sunTimes = SunTimes(
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
        )
        assertEquals("06:00:00", sunTimes.sunrise)
        assertEquals("18:00:00", sunTimes.sunset)
    }
}