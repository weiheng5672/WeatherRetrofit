package com.example.weatherretrofit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WeatherApp(
    modifier: Modifier,
    weatherViewModel: WeatherViewModel = viewModel()
) {

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        HomeScreen(
            weatherUiState = weatherViewModel.weather_UiState,
            retryAction = weatherViewModel::getData
        )

    }

}

@Composable
fun HomeScreen(
    retryAction: () -> Unit,
    weatherUiState: UiState,
    modifier: Modifier = Modifier
) {

    when (weatherUiState) {

        is UiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())

        is UiState.Success -> ResultScreen(
            dataResponse = weatherUiState.dataResponse,
            modifier = modifier.fillMaxWidth(),
        )

        is UiState.Error -> ErrorScreen(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )

    }

}


@Composable
fun ResultScreen(
    dataResponse: ApiResponse,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {

        Text(text = "信息: ${dataResponse.records}")

    }

}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}