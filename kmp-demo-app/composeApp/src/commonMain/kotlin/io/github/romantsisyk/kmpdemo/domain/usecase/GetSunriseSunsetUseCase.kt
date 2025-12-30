package io.github.romantsisyk.kmpdemo.domain.usecase

import io.github.romantsisyk.kmpdemo.domain.model.SunriseSunsetTimes
import io.github.romantsisyk.kmpdemo.domain.repository.SunriseSunsetRepository

class GetSunriseSunsetUseCase(
    private val repository: SunriseSunsetRepository
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        date: String = "today",
        timeZone: String? = null
    ): Result<SunriseSunsetTimes> {
        return repository.getSunriseSunsetTimes(latitude, longitude, date, timeZone)
    }
}
