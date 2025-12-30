package io.github.romantsisyk.kmpdemo.domain.model

data class City(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val timeZone: String
)

val defaultCities = listOf(
    City("Ternopil", 49.5535, 25.5948, "Europe/Kiev"),
    City("Wroclaw", 51.1079, 17.0385, "Europe/Warsaw"),
    City("Barcelona", 41.3874, 2.1686, "Europe/Madrid"),
    City("Chicago", 41.8781, -87.6298, "America/Chicago"),
    City("Tokyo", 35.6762, 139.6503, "Asia/Tokyo"),
    City("Sydney", -33.8688, 151.2093, "Australia/Sydney")
)
