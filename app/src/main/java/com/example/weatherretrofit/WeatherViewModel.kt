package com.example.weatherretrofit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherretrofit.model.ApiResponse
import com.example.weatherretrofit.network.Api
import kotlinx.coroutines.launch

sealed interface UiState {

    data class Success(
        val dataResponse: ApiResponse,
    ) : UiState

    data object Error : UiState
    data object Loading : UiState
}


class WeatherViewModel: ViewModel() {

    var weather_UiState: UiState by mutableStateOf(UiState.Loading)
        private set

    init {
        getAllData()
    }

    fun getAllData() {

        val authorization = "CWA-176D2330-A1B3-44B5-AF7A-FE13B35413AA"
        val timeFrom ="2024-07-01"
        val timeTo ="2024-07-31"

        viewModelScope.launch {

            weather_UiState = UiState.Loading

            weather_UiState = try {

                UiState.Success(

                    dataResponse = Api.retrofitService.getAllData(
                        authorization,
                        timeFrom,
                        timeTo
                    )

                )

            } catch (e: Exception) {
                UiState.Error
            }

        }

    }
}