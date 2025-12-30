package io.github.romantsisyk.kmpdemo.di

import io.github.romantsisyk.kmpdemo.data.repository.SunriseSunsetRepositoryImpl
import io.github.romantsisyk.kmpdemo.domain.repository.SunriseSunsetRepository
import io.github.romantsisyk.sunrisesunsetsdk.SunriseSunsetSDK
import org.koin.dsl.module

val iosModule = module {
    single { SunriseSunsetSDK.builder().build() }
    single<SunriseSunsetRepository> { SunriseSunsetRepositoryImpl(get()) }
}
