package io.github.romantsisyk.kmpdemo.data.repository

import io.github.romantsisyk.kmpdemo.domain.model.SunriseSunsetTimes
import io.github.romantsisyk.kmpdemo.domain.repository.SunriseSunsetRepository
import io.github.romantsisyk.sunrisesunsetsdk.SunriseSunsetSDK
import io.github.romantsisyk.sunrisesunsetsdk.models.SuccessApiResponse
import io.github.romantsisyk.sunrisesunsetsdk.utils.TimeZoneID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SunriseSunsetRepositoryImpl(
    private val sdk: SunriseSunsetSDK
) : SunriseSunsetRepository {

    override suspend fun getSunriseSunsetTimes(
        latitude: Double,
        longitude: Double,
        date: String,
        timeZone: String?
    ): Result<SunriseSunsetTimes> = withContext(Dispatchers.IO) {
        try {
            val tzid = timeZone?.let { TimeZoneID.fromId(it) }
            val response = sdk.getSunTimesAsync(
                lat = latitude,
                lng = longitude,
                date = date,
                formatted = 0,
                tzid = tzid
            )

            if (response is SuccessApiResponse) {
                val results = response.results
                Result.success(
                    SunriseSunsetTimes(
                        sunrise = results.sunrise,
                        sunset = results.sunset,
                        solarNoon = results.solarNoon,
                        dayLength = results.dayLength,
                        civilTwilightBegin = results.civilTwilightBegin,
                        civilTwilightEnd = results.civilTwilightEnd,
                        nauticalTwilightBegin = results.nauticalTwilightBegin,
                        nauticalTwilightEnd = results.nauticalTwilightEnd,
                        astronomicalTwilightBegin = results.astronomicalTwilightBegin,
                        astronomicalTwilightEnd = results.astronomicalTwilightEnd
                    )
                )
            } else {
                Result.failure(Exception("API returned error: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
