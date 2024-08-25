package com.example.weatherretrofit.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.weatherretrofit.InventoryTopAppBar
import com.example.weatherretrofit.navigation.NavigationRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToBarChart: () -> Unit,
    navigateToTable: () -> Unit,
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

        Column(
            modifier = Modifier
                .padding(innerPadding) // Apply the inner padding from Scaffold
                .fillMaxSize(), // Make the Column fill the available space

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center

        ) {

            Button(
                onClick =  navigateToBarChart
            ) {
                Text(
                    text = stringResource(NavigationRoutes.BarDestination.titleRes)
                )
            }

            Button(
                onClick =  navigateToTable
            ) {
                Text(
                    text = stringResource(NavigationRoutes.TableDestination.titleRes)
                )
            }

        }



    }

}




