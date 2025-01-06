package io.github.romantsisyk.common.domain.repository

import io.github.romantsisyk.common.domain.model.SunriseSunsetTimes
import io.github.romantsisyk.sunrisesunsetsdk.utils.TimeZoneID
import java.time.LocalDate

interface SunriseSunsetRepositoryInterface {
    suspend fun fetchSunriseSunsetTimes(
        latitude: Double,
        longitude: Double,
        date: String = LocalDate.now().toString(),
        timeZone: TimeZoneID? = null
    ): Result<SunriseSunsetTimes>
}


