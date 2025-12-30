package io.github.romantsisyk.sunrisesunsetsdk.network

import io.ktor.client.*
import io.ktor.client.engine.darwin.*

internal actual fun createPlatformHttpClient(): HttpClient = HttpClient(Darwin)
