package io.github.romantsisyk.kmpdemo.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.romantsisyk.kmpdemo.domain.model.City
import io.github.romantsisyk.kmpdemo.domain.model.SunriseSunsetTimes
import io.github.romantsisyk.kmpdemo.presentation.ui.components.CityDropdown
import io.github.romantsisyk.kmpdemo.presentation.ui.components.SunTimesCard
import io.github.romantsisyk.kmpdemo.presentation.viewmodel.SunriseSunsetViewModel
import io.github.romantsisyk.kmpdemo.presentation.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: SunriseSunsetViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedCity by viewModel.selectedCity.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sunrise Sunset KMP") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CityDropdown(
                cities = viewModel.cities,
                selectedCity = selectedCity,
                onCitySelected = { viewModel.selectCity(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            when (val state = uiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Loading sun times...")
                }

                is UiState.Success -> {
                    SunTimesContent(
                        city = selectedCity,
                        times = state.data,
                        onRefresh = { viewModel.refresh() }
                    )
                }

                is UiState.Error -> {
                    ErrorContent(
                        message = state.message,
                        onRetry = { viewModel.refresh() }
                    )
                }
            }
        }
    }
}

@Composable
private fun SunTimesContent(
    city: City,
    times: SunriseSunsetTimes,
    onRefresh: () -> Unit
) {
    Text(
        text = city.name,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
    )

    Text(
        text = "Lat: ${city.latitude}, Lng: ${city.longitude}",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(16.dp))

    SunTimesCard(
        title = "Main Times",
        items = listOf(
            "Sunrise" to times.sunrise,
            "Sunset" to times.sunset,
            "Solar Noon" to times.solarNoon,
            "Day Length" to times.dayLength
        )
    )

    Spacer(modifier = Modifier.height(16.dp))

    SunTimesCard(
        title = "Civil Twilight",
        items = listOf(
            "Begin" to times.civilTwilightBegin,
            "End" to times.civilTwilightEnd
        )
    )

    Spacer(modifier = Modifier.height(16.dp))

    SunTimesCard(
        title = "Nautical Twilight",
        items = listOf(
            "Begin" to times.nauticalTwilightBegin,
            "End" to times.nauticalTwilightEnd
        )
    )

    Spacer(modifier = Modifier.height(16.dp))

    SunTimesCard(
        title = "Astronomical Twilight",
        items = listOf(
            "Begin" to times.astronomicalTwilightBegin,
            "End" to times.astronomicalTwilightEnd
        )
    )

    Spacer(modifier = Modifier.height(24.dp))

    Button(onClick = onRefresh) {
        Text("Refresh")
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Error",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}
