package com.example.weatherretrofit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        getData()
    }

    fun getData() {

        val authorization = "CWA-176D2330-A1B3-44B5-AF7A-FE13B35413AA"
        val limit = 10
        val date ="2024-01-01"

        viewModelScope.launch {

            weather_UiState = UiState.Loading

            weather_UiState = try {

                UiState.Success(

                    dataResponse = Api.retrofitService.getData(
                        authorization,
                        limit.toString(),
                        date
                    ),

                )

            } catch (e: Exception) {
                UiState.Error
            }

        }

    }
}