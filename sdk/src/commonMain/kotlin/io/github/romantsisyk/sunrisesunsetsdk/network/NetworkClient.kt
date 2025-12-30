package io.github.romantsisyk.sunrisesunsetsdk.network

import io.github.romantsisyk.sunrisesunsetsdk.config.SdkConfig
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*

/**
 * A network client for making HTTP requests to the sunrise-sunset API.
 *
 * This class is responsible for creating and managing HTTP requests and responses
 * using the Ktor library. It allows setting custom base URLs and timeout durations
 * for API interactions.
 *
 * @property baseUrl The base URL for the API (default: [SdkConfig.DEFAULT_BASE_URL]).
 * @property timeoutSeconds The timeout duration for network requests, in seconds (default: [SdkConfig.DEFAULT_TIMEOUT_SECONDS]).
 */
class NetworkClient(
    private val baseUrl: String = SdkConfig.DEFAULT_BASE_URL,
    private val timeoutSeconds: Long = SdkConfig.DEFAULT_TIMEOUT_SECONDS
) {
    private val client: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(JsonParser.instance)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = timeoutSeconds * 1000
            connectTimeoutMillis = timeoutSeconds * 1000
            socketTimeoutMillis = timeoutSeconds * 1000
        }
    }

    /**
     * Executes a GET request to the specified URL and returns the response as a string.
     *
     * @param url The full URL to which the GET request is made.
     * @return The response body as a string.
     * @throws Exception If the response is unsuccessful or the body is empty.
     */
    suspend fun get(url: String): String {
        val response: HttpResponse = client.get(url)
        if (response.status.value !in 200..299) {
            throw Exception("HTTP error code: ${response.status.value}, message: ${response.status.description}")
        }
        return response.bodyAsText()
    }

    /**
     * Retrieves the configured timeout duration for network requests.
     *
     * This method provides access to the timeout setting, allowing verification
     * of the configuration during testing or debugging.
     *
     * @return The timeout duration in seconds.
     */
    fun getTimeoutSeconds(): Long = timeoutSeconds

    /**
     * Closes the HTTP client and releases resources.
     */
    fun close() {
        client.close()
    }
}
