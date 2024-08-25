package com.example.weatherretrofit.ui

import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherretrofit.R
import com.example.weatherretrofit.model.ApiResponse
import com.example.weatherretrofit.model.StationObsTime
import com.example.weatherretrofit.navigation.NavigationRoutes
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarChatScreen(
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

        BartChatHost(
            weatherUiState = weatherViewModel.weather_UiState,
            retryAction = weatherViewModel::getData,
            modifier = Modifier
                .padding(innerPadding) // Apply the inner padding from Scaffold
                .fillMaxSize()
        )

    }

}

@Composable
fun BartChatHost(
    retryAction: () -> Unit,
    weatherUiState: UiState,
    modifier: Modifier = Modifier
) {

    //UiState類型中 只有 Success 有資料
    //如果沒有這個 when
    //編譯器會因為無法確認 weatherUiState 裡面 到底有沒有資料
    //無法使用 weatherUiState.dataResponse 這樣去存取裡面的資料
    when (weatherUiState) {

        is UiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())

        is UiState.Success -> BarChatResultScreen(
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
fun BarChatResultScreen(
    dataResponse: ApiResponse,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {

        Text(dataResponse.records.location[0].station.StationName)

        //這裡是關鍵
        //這一大串東西看著複雜 也是很複雜
        //總之這一串東西 把原始資料中的 嵌套結構中的某個List 給取出來
        //台北地區的一個觀測點 七月每天的氣象數據
        WeatherBarChat(dataResponse.records.location[0].stationObsTimes.stationObsTime)

    }

}

@Composable
fun WeatherBarChat(
    stationObsTime: List<StationObsTime>
) {

    // stationObsTime 是一個 List
    // 每個元素 代表 每天的氣象數據
    // 萃取出Precipitation部分的資料，形成新的List
    val precipitationList = stationObsTime.map { it.weatherElements.Precipitation }

    // 萃取出Date部分的資料 作為x軸的標示
    val dateList = stationObsTime.map { it.Date }

    // 將字串型態的 Precipitation(降雨量) 的 List 轉換成 Float型態的List
    // 原字串中的T 代表有下雨但 雨量小於 0.1mm
    // 把降雨量從字串轉換成數字 目的是 要使用 MPAndroid 做圖
    val floatList: List<Float> = precipitationList.map { value ->
        value.toFloatOrNull() ?: 0f
    }

    AndroidView(

        modifier = Modifier.fillMaxSize(),

        factory = { context ->
            // 创建 BarChart 实例
            val barChart = BarChart(context)

            // 初始化 BarEntries 列表
            val barEntriesList = ArrayList<BarEntry>().apply {
                floatList.forEachIndexed { index, value ->
                    add(BarEntry((index + 1).toFloat(), value))
                }
            }

            // 创建 BarDataSet 和 BarData
            val barDataSet = BarDataSet(barEntriesList, "Bar Chart Data").apply {
                valueTextColor = Color.RED
                setColor(ContextCompat.getColor(context, R.color.purple_200))
                valueTextSize = 12f
            }

            val barData = BarData(barDataSet)

            // 设置 BarChart 数据和属性
            barChart.apply {
                data = barData
                description.isEnabled = false

                // 设置 X 轴显示日期
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    valueFormatter = IndexAxisValueFormatter( dateList )
                    granularity = 1f // 保證標籤一一對應
                    labelRotationAngle = -90f // 旋轉標籤以避免重疊
                }

                axisLeft.axisMinimum = 0f
                axisRight.isEnabled = false

                invalidate() // 刷新图表
            }

            barChart

        }
    )
}