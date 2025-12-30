package io.github.romantsisyk.kmpdemo.presentation.viewmodel

import io.github.romantsisyk.kmpdemo.domain.model.City
import io.github.romantsisyk.kmpdemo.domain.model.defaultCities
import io.github.romantsisyk.kmpdemo.domain.usecase.GetSunriseSunsetUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SunriseSunsetViewModel(
    private val useCase: GetSunriseSunsetUseCase
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _selectedCity = MutableStateFlow(defaultCities.first())
    val selectedCity: StateFlow<City> = _selectedCity.asStateFlow()

    val cities: List<City> = defaultCities

    init {
        fetchSunriseSunsetData()
    }

    fun selectCity(city: City) {
        _selectedCity.value = city
        fetchSunriseSunsetData()
    }

    fun refresh() {
        fetchSunriseSunsetData()
    }

    private fun fetchSunriseSunsetData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val city = _selectedCity.value
            useCase(
                latitude = city.latitude,
                longitude = city.longitude,
                date = "today",
                timeZone = city.timeZone
            ).fold(
                onSuccess = { times ->
                    _uiState.value = UiState.Success(times)
                },
                onFailure = { error ->
                    _uiState.value = UiState.Error(error.message ?: "Unknown error occurred")
                }
            )
        }
    }
}
