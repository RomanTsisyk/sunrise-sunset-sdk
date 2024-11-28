package io.github.romantsisyk.sunrisesunsetsdk

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SunriseSunsetSdkBuilderTest {


    @Test
    fun `should create SDK with default settings`() {
        val sdk = SunriseSunsetSdkBuilder().build()
        assertNotNull(sdk)
    }
}