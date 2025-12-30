package io.github.romantsisyk.kmpdemo

import androidx.compose.ui.window.ComposeUIViewController
import io.github.romantsisyk.kmpdemo.di.commonModule
import io.github.romantsisyk.kmpdemo.di.iosModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {
    App()
}

fun initKoin() {
    startKoin {
        modules(commonModule, iosModule)
    }
}
