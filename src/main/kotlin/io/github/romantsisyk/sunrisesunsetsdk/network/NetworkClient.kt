package io.github.romantsisyk.sunrisesunsetsdk.network

import io.github.romantsisyk.sunrisesunsetsdk.config.SdkConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/**
 * A network client for making HTTP requests to the sunrise-sunset API.
 *
 * This class is responsible for creating and managing HTTP requests and responses
 * using the OkHttp library. It allows setting custom base URLs and timeout durations
 * for API interactions.
 *
 * @property baseUrl The base URL for the API (default: [SdkConfig.DEFAULT_BASE_URL]).
 * @property timeoutSeconds The timeout duration for network requests, in seconds (default: [SdkConfig.DEFAULT_TIMEOUT_SECONDS]).
 * @property client The OkHttpClient instance used for executing requests.
 */
class NetworkClient(
    private val baseUrl: String = SdkConfig.DEFAULT_BASE_URL,
    private val timeoutSeconds: Long = SdkConfig.DEFAULT_TIMEOUT_SECONDS,
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
        .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
        .writeTimeout(timeoutSeconds, TimeUnit.SECONDS)
        .build()
) {

    /**
     * Executes a GET request to the specified URL and returns the response as a string.
     *
     * @param url The full URL to which the GET request is made.
     * @return The response body as a string.
     * @throws Exception If the response is unsuccessful or the body is empty.
     */
    fun get(url: String): String {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw Exception("HTTP error code: ${response.code}, message: ${response.message}")
            }

            return response.body?.string() ?: throw Exception("Empty response body.")
        }
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
}