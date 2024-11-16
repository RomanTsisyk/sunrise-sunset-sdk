package io.github.romantsisyk.sunrisesunsetsdk.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SunTimes(
    val sunrise: String,
    val sunset: String,
    @SerialName("solar_noon") val solarNoon: String,
    @SerialName("day_length") val dayLength: String,
    @SerialName("civil_twilight_begin") val civilTwilightBegin: String,
    @SerialName("civil_twilight_end") val civilTwilightEnd: String,
    @SerialName("nautical_twilight_begin") val nauticalTwilightBegin: String,
    @SerialName("nautical_twilight_end") val nauticalTwilightEnd: String,
    @SerialName("astronomical_twilight_begin") val astronomicalTwilightBegin: String,
    @SerialName("astronomical_twilight_end") val astronomicalTwilightEnd: String
)
