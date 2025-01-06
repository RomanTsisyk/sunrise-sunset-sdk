package io.github.romantsisyk.sunrisesunsetsdk.network

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class JsonParserTest {

    @Test
    fun `should have ignoreUnknownKeys set to true`() {
        assertTrue(JsonParser.instance.configuration.ignoreUnknownKeys)
    }

    @Test
    fun `should have isLenient set to true`() {
        assertTrue(JsonParser.instance.configuration.isLenient)
    }

    @Test
    fun `should allow special floating point values`() {
        assertTrue(JsonParser.instance.configuration.allowSpecialFloatingPointValues)
    }
}