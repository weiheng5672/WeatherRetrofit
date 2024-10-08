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

    data object TableDestination : NavigationRoutes(
        route = "line",
        titleRes = R.string.table_title
    )


    data object BarDestination : NavigationRoutes(
        route = "bar",
        titleRes = R.string.bar_chart_title
    )


}