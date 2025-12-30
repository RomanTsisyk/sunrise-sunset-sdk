package io.github.romantsisyk.kmpdemo.presentation.viewmodel

import io.github.romantsisyk.kmpdemo.domain.model.SunriseSunsetTimes

sealed class UiState {
    data object Loading : UiState()
    data class Success(val data: SunriseSunsetTimes) : UiState()
    data class Error(val message: String) : UiState()
}
