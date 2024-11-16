package io.github.romantsisyk.sunrisesunsetsdk.network

import kotlinx.serialization.json.Json

object JsonParser {
    val instance: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        allowSpecialFloatingPointValues = true
    }
}