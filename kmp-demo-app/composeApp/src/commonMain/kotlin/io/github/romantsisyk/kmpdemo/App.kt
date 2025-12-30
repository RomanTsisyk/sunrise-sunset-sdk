package io.github.romantsisyk.kmpdemo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import io.github.romantsisyk.kmpdemo.presentation.ui.MainScreen
import io.github.romantsisyk.kmpdemo.presentation.viewmodel.SunriseSunsetViewModel
import org.koin.compose.koinInject

@Composable
fun App() {
    MaterialTheme {
        val viewModel: SunriseSunsetViewModel = koinInject()
        MainScreen(viewModel = viewModel)
    }
}
