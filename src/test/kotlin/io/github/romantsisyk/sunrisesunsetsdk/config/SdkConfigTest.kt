package io.github.romantsisyk.sunrisesunsetsdk.config

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SdkConfigTest {

    @Test
    fun `should have correct default base URL`() {
        assertEquals("https://api.sunrise-sunset.org/json", SdkConfig.DEFAULT_BASE_URL)
    }

    @Test
    fun `should have correct default timeout`() {
        assertEquals(30L, SdkConfig.DEFAULT_TIMEOUT_SECONDS)
    }
}