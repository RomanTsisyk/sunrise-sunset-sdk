package io.github.romantsisyk.sunrisesunsetsdk.models

import io.github.romantsisyk.sunrisesunsetsdk.utils.TimeZoneID
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TimeZoneIDTest {

    @Test
    fun `should return correct TimeZoneID for valid ID`() {
        val id = "America/New_York"
        val timeZoneID = TimeZoneID.fromId(id)
        assertNotNull(timeZoneID)
        assertEquals(TimeZoneID.AMERICA_NEW_YORK, timeZoneID)
    }

    @Test
    fun `should return null for invalid ID`() {
        val id = "Invalid/ID"
        val timeZoneID = TimeZoneID.fromId(id)
        assertNull(timeZoneID)
    }
}
