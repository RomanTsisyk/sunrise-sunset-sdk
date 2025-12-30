package io.github.romantsisyk.kmpdemo.domain.repository

import io.github.romantsisyk.kmpdemo.domain.model.SunriseSunsetTimes

interface SunriseSunsetRepository {
    suspend fun getSunriseSunsetTimes(
        latitude: Double,
        longitude: Double,
        date: String,
        timeZone: String?
    ): Result<SunriseSunsetTimes>
}
