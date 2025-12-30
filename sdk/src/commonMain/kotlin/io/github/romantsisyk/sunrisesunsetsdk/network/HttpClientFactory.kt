package io.github.romantsisyk.sunrisesunsetsdk.network

import io.ktor.client.*

/**
 * Creates a platform-specific Ktor HttpClient.
 */
internal expect fun createPlatformHttpClient(): HttpClient
