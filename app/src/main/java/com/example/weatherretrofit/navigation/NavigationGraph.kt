package com.example.weatherretrofit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherretrofit.ui.BarChatScreen
import com.example.weatherretrofit.ui.HomeScreen
import com.example.weatherretrofit.ui.TableScreen


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

            HomeScreen(

                navigateToBarChart = { navController.navigate(NavigationRoutes.BarDestination.route) },

                navigateToTable = { navController.navigate(NavigationRoutes.TableDestination.route) }

            )

        }


        composable(route = NavigationRoutes.BarDestination.route) {

            BarChatScreen(

                navigateBack = { navController.navigate(NavigationRoutes.HomeDestination.route) },

                )

        }

        composable(route = NavigationRoutes.TableDestination.route) {

            TableScreen(
                navigateBack = { navController.navigate(NavigationRoutes.HomeDestination.route) }
            )

        }



    }

}