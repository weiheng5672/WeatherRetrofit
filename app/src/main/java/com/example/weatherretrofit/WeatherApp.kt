package com.example.weatherretrofit

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weatherretrofit.navigation.NavigationGraph

@Composable
fun WeatherApp(
    navController: NavHostController = rememberNavController()
){
    NavigationGraph(navController = navController)
}

