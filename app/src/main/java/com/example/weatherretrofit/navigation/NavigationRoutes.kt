package com.example.weatherretrofit.navigation

import com.example.weatherretrofit.R

sealed class NavigationRoutes(
    val route: String,
    val titleRes: Int
) {

    data object HomeDestination : NavigationRoutes(
        route = "home",
        titleRes = R.string.home_title
    )

}