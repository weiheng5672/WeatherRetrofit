package com.example.weatherretrofit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherretrofit.ui.HomeScreen


@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        //預設顯示 HomeScreen
        startDestination = NavigationRoutes.HomeDestination.route,
        modifier = modifier
    ) {

        composable(route = NavigationRoutes.HomeDestination.route) {

            HomeScreen()

        }


    }

}