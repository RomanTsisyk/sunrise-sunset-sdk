package io.github.romantsisyk.sunrisesunsetsdk

import io.github.romantsisyk.sunrisesunsetsdk.config.SdkConfig
import io.github.romantsisyk.sunrisesunsetsdk.exceptions.*
import io.github.romantsisyk.sunrisesunsetsdk.models.ApiResponse
import io.github.romantsisyk.sunrisesunsetsdk.models.ErrorApiResponse
import io.github.romantsisyk.sunrisesunsetsdk.models.SuccessApiResponse
import io.github.romantsisyk.sunrisesunsetsdk.network.NetworkClient
import io.github.romantsisyk.sunrisesunsetsdk.utils.TimeZoneID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class SunriseSunsetSDK(
    private val networkClient: NetworkClient,
    private val jsonParser: Json
) {

    companion object {
        // Constants for error statuses
        private const val STATUS_OK = "OK"
        const val STATUS_INVALID_REQUEST = "INVALID_REQUEST"
        const val STATUS_INVALID_DATE = "INVALID_DATE"
        const val STATUS_INVALID_TZID = "INVALID_TZID"
        const val STATUS_UNKNOWN_ERROR = "UNKNOWN_ERROR"

        // Error messages
        private const val ERROR_MISSING_STATUS = "Missing 'status' field in response."
        private const val ERROR_MALFORMED_RESPONSE = "Malformed API response: missing required fields."
        private const val ERROR_DESERIALIZATION = "Failed to deserialize API response."
        private const val ERROR_UNEXPECTED = "Unexpected status: "

        /**
         * Creates an instance of SunriseSunsetSDK using the builder.
         */
        fun builder(): SunriseSunsetSdkBuilder = SunriseSunsetSdkBuilder()
    }

    /**
     * Fetches sunrise and sunset times synchronously.
     */
    @Throws(SunriseSunsetException::class)
    fun getSunTimes(
        lat: Double,
        lng: Double,
        date: String? = null,
        formatted: Int? = null,
        tzid: TimeZoneID? = null
    ): ApiResponse {
        val url = buildUrl(lat, lng, date, formatted, tzid)
        return try {
            val responseBody = networkClient.get(url)
            val apiResponse = parseResponse(responseBody)
            handleApiResponse(apiResponse)
        } catch (e: SunriseSunsetException) {
            throw e // Re-throw known exceptions
        } catch (e: Exception) {
            throw SunriseSunsetException("Failed to fetch sun times.", e)
        }
    }

    /**
     * Fetches sunrise and sunset times asynchronously using coroutines.
     */
    suspend fun getSunTimesAsync(
        lat: Double,
        lng: Double,
        date: String? = null,
        formatted: Int? = null,
        tzid: TimeZoneID? = null
    ): ApiResponse = withContext(Dispatchers.IO) {
        val url = buildUrl(lat, lng, date, formatted, tzid)
        try {
            val responseBody = networkClient.get(url)
            val apiResponse = parseResponse(responseBody)
            handleApiResponse(apiResponse)
        } catch (e: Exception) {
            throw SunriseSunsetException("Failed to fetch sun times asynchronously.", e)
        }
    }

    /**
     * Builds the request URL with the provided parameters.
     */
    private fun buildUrl(
        lat: Double,
        lng: Double,
        date: String?,
        formatted: Int?,
        tzid: TimeZoneID?
    ): String {
        return buildString {
            append("${SdkConfig.DEFAULT_BASE_URL}?lat=$lat&lng=$lng")
            date?.let { append("&date=$it") }
            formatted?.let { append("&formatted=$it") }
            tzid?.let { append("&tzid=${it.id}") }
        }
    }

    /**
     * Parses the JSON response and handles status codes.
     */
    @OptIn(ExperimentalSerializationApi::class)
    private fun parseResponse(responseBody: String): ApiResponse {
        return try {
            val jsonElement = jsonParser.parseToJsonElement(responseBody)
            val status = jsonElement.jsonObject["status"]?.jsonPrimitive?.content
                ?: throw SunriseSunsetException(ERROR_MISSING_STATUS)

            when (status) {
                STATUS_OK -> jsonParser.decodeFromString<SuccessApiResponse>(responseBody)
                STATUS_INVALID_REQUEST,
                STATUS_INVALID_DATE,
                STATUS_INVALID_TZID,
                STATUS_UNKNOWN_ERROR -> jsonParser.decodeFromString<ErrorApiResponse>(responseBody)
                else -> throw SunriseSunsetException("$ERROR_UNEXPECTED$status")
            }
        } catch (e: MissingFieldException) {
            throw SunriseSunsetException("$ERROR_MALFORMED_RESPONSE $responseBody", e)
        } catch (e: kotlinx.serialization.SerializationException) {
            throw SunriseSunsetException("$ERROR_DESERIALIZATION $responseBody", e)
        } catch (e: Exception) {
            throw SunriseSunsetException("Unexpected error while parsing API response.", e)
        }
    }

    /**
     * Handles the ApiResponse and throws appropriate exceptions for errors.
     */
    private fun handleApiResponse(apiResponse: ApiResponse): ApiResponse {
        if (apiResponse is ErrorApiResponse) {
            when (apiResponse.status) {
                STATUS_INVALID_REQUEST -> throw InvalidRequestException("Invalid request.")
                STATUS_INVALID_DATE -> throw InvalidDateException("Invalid date.")
                STATUS_INVALID_TZID -> throw InvalidTimeZoneException("Invalid time zone.")
                STATUS_UNKNOWN_ERROR -> throw SunriseSunsetException("Unknown error.")
                else -> throw SunriseSunsetException("$ERROR_UNEXPECTED${apiResponse.status}")
            }
        }
        return apiResponse
    }
}
