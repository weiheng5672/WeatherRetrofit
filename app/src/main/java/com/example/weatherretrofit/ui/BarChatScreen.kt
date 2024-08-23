package com.example.weatherretrofit.ui

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherretrofit.R
import com.example.weatherretrofit.model.ApiResponse
import com.example.weatherretrofit.navigation.NavigationRoutes
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarChatScreen(
    navigateBack: () -> Unit,
){
    val weatherViewModel: WeatherViewModel = viewModel()

    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(NavigationRoutes.BarDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) {
        innerPadding ->

        ScreenHost(
            weatherUiState = weatherViewModel.weather_UiState,
            retryAction = weatherViewModel::getData,
            modifier = Modifier
                .padding(innerPadding) // Apply the inner padding from Scaffold
                .fillMaxSize()
        )

    }

}

@Composable
fun ScreenHost(
    retryAction: () -> Unit,
    weatherUiState: UiState,
    modifier: Modifier = Modifier
) {

    when (weatherUiState) {

        is UiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())

        is UiState.Success -> ResultScreen2(
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
fun ResultScreen2(
    dataResponse: ApiResponse,
    modifier: Modifier = Modifier
) {

    // 初始化 BarEntries 列表
    val taipeiLocation = dataResponse.records.location.find { it.station.StationNameEN == "TAIPEI" }

    val extractDateAndPrecipitation = taipeiLocation?.stationObsTimes?.stationObsTime?.map {
        Pair(
            it.Date.toFloat(),
            it.weatherElements.Precipitation.toFloat()
        )
    } ?: emptyList()

    val barEntries = mutableListOf<BarEntry>()
    var index = 1

    val stationObsTimes = dataResponse.records.location[0].stationObsTimes.stationObsTime
    for (obsTime in stationObsTimes) {
        val precipitation = obsTime.weatherElements.Precipitation
        val precipitationValue = if (precipitation == "T") 0f else precipitation.toFloatOrNull() ?: 0f
        barEntries.add(BarEntry(index.toFloat(), precipitationValue))
        index++
    }

    AndroidView(

        modifier = Modifier.fillMaxSize(),

        factory = { context ->
            // 创建 BarChart 实例
            val barChart = BarChart(context)

            // 创建 BarDataSet 和 BarData
            val barDataSet = BarDataSet(barEntries, "Bar Chart Data").apply {
                valueTextColor = Color.RED
                setColor(ContextCompat.getColor(context, R.color.purple_200))
                valueTextSize = 25f
            }

            val barData = BarData(barDataSet)

            // 设置 BarChart 数据和属性
            barChart.apply {
                data = barData
                description.isEnabled = false
                invalidate() // 刷新图表
            }

            barChart

        }
    )

}