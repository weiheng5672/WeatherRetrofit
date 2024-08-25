package com.example.weatherretrofit.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherretrofit.ErrorScreen
import com.example.weatherretrofit.InventoryTopAppBar
import com.example.weatherretrofit.LoadingScreen
import com.example.weatherretrofit.R
import com.example.weatherretrofit.UiState
import com.example.weatherretrofit.WeatherViewModel
import com.example.weatherretrofit.model.Location
import com.example.weatherretrofit.navigation.NavigationRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    weatherViewModel: WeatherViewModel = viewModel()
){

    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(NavigationRoutes.HomeDestination.titleRes),
                canNavigateBack = false,
            )
        }
    ) {
            innerPadding ->

        HomeBody(

            weatherUiState = weatherViewModel.weather_UiState,

            retryAction = weatherViewModel::getData,


            // 設置內容的內邊距
            // 確保內容不會被 topBar 和 floatingActionButton 遮擋
            contentPadding = innerPadding

        )

    }

}

@Composable
private fun HomeBody(

    weatherUiState: UiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    retryAction: () -> Unit,

) {

    when (weatherUiState) {

        is UiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())

        is UiState.Success -> PrecipitationList(

            itemList = weatherUiState.dataResponse.records.location,

            contentPadding = contentPadding

        )

        is UiState.Error -> ErrorScreen(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )

    }

}

@Composable
private fun PrecipitationList(
    itemList: List<Location>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {

        items(items = itemList) { item ->

            PrecipitationItem(
                item = item,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))

            )

        }

    }
}

@Composable
private fun PrecipitationItem(

    item: Location,
    modifier: Modifier = Modifier

) {

    val precipitationList = item.stationObsTimes.stationObsTime.map { it.weatherElements.Precipitation }

    val acc = precipitationList.mapNotNull { it.toDoubleOrNull() }.sum()

    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.station.StationName,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))

                Text(
                    text = "月累計雨量:$acc mm",
                    style = MaterialTheme.typography.titleMedium
                )

            }

            Text(
                text = item.station.StationAttribute,
                style = MaterialTheme.typography.titleMedium
            )

        }
    }
}






