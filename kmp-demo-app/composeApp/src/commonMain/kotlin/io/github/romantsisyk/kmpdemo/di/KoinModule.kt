package io.github.romantsisyk.kmpdemo.di

import io.github.romantsisyk.kmpdemo.domain.usecase.GetSunriseSunsetUseCase
import io.github.romantsisyk.kmpdemo.presentation.viewmodel.SunriseSunsetViewModel
import org.koin.dsl.module

val commonModule = module {
    factory { GetSunriseSunsetUseCase(get()) }
    factory { SunriseSunsetViewModel(get()) }
}
