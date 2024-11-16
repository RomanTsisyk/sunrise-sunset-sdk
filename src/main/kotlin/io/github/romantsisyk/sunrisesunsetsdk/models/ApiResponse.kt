package io.github.romantsisyk.sunrisesunsetsdk.models

import kotlinx.serialization.Serializable


@Serializable
sealed class ApiResponse {
    abstract val status: String
}

@Serializable
data class SuccessApiResponse(
    val results: SunTimes,
    override val status: String,
    val tzid: String
) : ApiResponse()

@Serializable
data class ErrorApiResponse(
    val results: String? = null,
    override val status: String
) : ApiResponse()
