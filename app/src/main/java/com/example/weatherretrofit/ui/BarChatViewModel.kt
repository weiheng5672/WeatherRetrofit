package com.example.weatherretrofit.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherretrofit.model.ApiResponse
import com.example.weatherretrofit.navigation.NavigationRoutes
import com.example.weatherretrofit.network.Api
import kotlinx.coroutines.launch


sealed interface ChartUiState {

    data class Success(
        val dataResponse: ApiResponse,
    ) : ChartUiState

    data object Error : ChartUiState
    data object Loading : ChartUiState
}

class BarChatViewModel(
    savedStateHandle: SavedStateHandle ,
): ViewModel() {

    // itemId 是整數型態
    // itemIdArg 是字串
    // 在 導覽程式中 會將 ItemDetailsDestination.itemIdArg 指定為Int
    private val itemId: Int = checkNotNull( savedStateHandle[NavigationRoutes.BarDestination.itemIdArg])

    var precipitation_UiState: ChartUiState by mutableStateOf(ChartUiState.Loading)
        private set

    init {
        getOneData()
    }

    fun getOneData() {

        val authorization = "CWA-176D2330-A1B3-44B5-AF7A-FE13B35413AA"
        val stationId = itemId.toString()
        val timeFrom ="2024-07-01"
        val timeTo ="2024-07-31"

        viewModelScope.launch {

            precipitation_UiState = ChartUiState.Loading

            precipitation_UiState = try {

                ChartUiState.Success(

                    dataResponse = Api.retrofitService.getOneData(
                        authorization,
                        stationId,
                        timeFrom,
                        timeTo
                    )

                )

            } catch (e: Exception) {
                ChartUiState.Error
            }

        }

    }




}
