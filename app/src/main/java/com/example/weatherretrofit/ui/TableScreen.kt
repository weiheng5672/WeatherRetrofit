package com.example.weatherretrofit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherretrofit.ErrorScreen
import com.example.weatherretrofit.InventoryTopAppBar
import com.example.weatherretrofit.LoadingScreen
import com.example.weatherretrofit.model.ApiResponse
import com.example.weatherretrofit.model.StationObsTime
import com.example.weatherretrofit.navigation.NavigationRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableScreen(
    navigateBack: () -> Unit
) {

    val weatherViewModel: WeatherViewModel = viewModel()

    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(NavigationRoutes.BarDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ){
        innerPadding ->

        TableHost(
            weatherUiState = weatherViewModel.weather_UiState,
            retryAction = weatherViewModel::getData,
            modifier = Modifier
                .padding(innerPadding) // Apply the inner padding from Scaffold
                .fillMaxSize()
        )

    }

}

@Composable
fun TableHost(
    retryAction: () -> Unit,
    weatherUiState: UiState,
    modifier: Modifier = Modifier
) {

    when (weatherUiState) {

        is UiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())

        is UiState.Success -> TableResultScreen(
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
fun TableResultScreen(
    dataResponse: ApiResponse,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        
        Text(text = dataResponse.records.location[0].station.StationName)

        WeatherTable(dataResponse.records.location[0].stationObsTimes.stationObsTime)

    }

}

@Composable
fun WeatherTable(
    stationObsTime: List<StationObsTime>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .background(Color.LightGray)
        ){
            Text(
                "日期",
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            )

            Text(
                "降水量",
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(stationObsTime.size) { index ->
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(Color.LightGray)
                ) {
                    // Column 1: Date
                    Text(
                        text = stationObsTime[index].Date,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                    )

                    // Column 2: Number
                    Text(
                        text = "${stationObsTime[index].weatherElements.Precipitation}mm",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                    )

                }
            }
        }

    }
}



