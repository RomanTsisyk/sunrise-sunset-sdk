package io.github.romantsisyk.sunrisesunsetsdk.network

import io.github.romantsisyk.sunrisesunsetsdk.config.SdkConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class NetworkClient(
    private val baseUrl: String = SdkConfig.DEFAULT_BASE_URL,
    timeoutSeconds: Long = SdkConfig.DEFAULT_TIMEOUT_SECONDS,
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
        .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
        .writeTimeout(timeoutSeconds, TimeUnit.SECONDS)
        .build()
) {
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
}