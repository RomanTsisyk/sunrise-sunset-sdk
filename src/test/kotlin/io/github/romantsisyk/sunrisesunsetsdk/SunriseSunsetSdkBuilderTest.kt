package io.github.romantsisyk.sunrisesunsetsdk

import io.github.romantsisyk.sunrisesunsetsdk.network.NetworkClient
import io.mockk.mockk
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SunriseSunsetSdkBuilderTest {


    @Test
    fun `should create SDK with default settings`() {
        val sdk = SunriseSunsetSdkBuilder().build()
        assertNotNull(sdk)
    }

    @Test
    fun `should set custom JSON parser`() {
        val customJson = Json { prettyPrint = true }
        val sdk = SunriseSunsetSdkBuilder()
            .setJsonParser(customJson)
            .build()

        assertEquals(customJson, sdk.jsonParser)
    }

    @Test
    fun `should set custom base URL`() {
        val customUrl = "https://custom-api.example.com"
        val sdk = SunriseSunsetSdkBuilder()
            .setBaseUrl(customUrl)
            .build()

        val networkClient = sdk.javaClass.getDeclaredField("networkClient").apply { isAccessible = true }
            .get(sdk) as NetworkClient
        assertEquals(customUrl, networkClient.javaClass.getDeclaredField("baseUrl").apply { isAccessible = true }.get(networkClient))
    }

    @Test
    fun `should set custom timeout duration`() {
        val customTimeout = 150L
        val sdk = SunriseSunsetSdkBuilder()
            .setTimeoutSeconds(customTimeout)
            .build()

        assertEquals(customTimeout, sdk.networkClient.getTimeoutSeconds())
    }

    @Test
    fun `should use custom network client`() {
        val mockNetworkClient = mockk<NetworkClient>(relaxed = true)

        val sdk = SunriseSunsetSdkBuilder()
            .setNetworkClient(mockNetworkClient)
            .build()

        val networkClientField = sdk.javaClass.getDeclaredField("networkClient").apply { isAccessible = true }
        assertEquals(mockNetworkClient, networkClientField.get(sdk))
    }
}