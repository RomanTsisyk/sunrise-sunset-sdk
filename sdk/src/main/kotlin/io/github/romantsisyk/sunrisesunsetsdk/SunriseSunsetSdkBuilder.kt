package io.github.romantsisyk.sunrisesunsetsdk

import androidx.annotation.VisibleForTesting
import io.github.romantsisyk.sunrisesunsetsdk.config.SdkConfig
import io.github.romantsisyk.sunrisesunsetsdk.network.JsonParser
import io.github.romantsisyk.sunrisesunsetsdk.network.NetworkClient
import kotlinx.serialization.json.Json

class SunriseSunsetSdkBuilder {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var baseUrl: String = SdkConfig.DEFAULT_BASE_URL
    private var timeoutSeconds: Long = SdkConfig.DEFAULT_TIMEOUT_SECONDS
    private var json: Json = JsonParser.instance
    private var networkClient: NetworkClient? = null

    /**
     * Sets a custom base URL for the API.
     */
    fun setBaseUrl(url: String) = apply { this.baseUrl = url }

    /**
     * Sets a custom timeout duration.
     */
    fun setTimeoutSeconds(seconds: Long) = apply { this.timeoutSeconds = seconds }

    /**
     * Sets a custom JSON parser.
     */
    fun setJsonParser(json: Json) = apply { this.json = json }

    /**
     * Sets a custom network client.
     */
    fun setNetworkClient(client: NetworkClient) = apply { this.networkClient = client }

    /**
     * Builds the SunriseSunsetSDK instance.
     */
    fun build(): SunriseSunsetSDK {
        val client = networkClient ?: NetworkClient(baseUrl, timeoutSeconds)
        return SunriseSunsetSDK(client, json)
    }
}
