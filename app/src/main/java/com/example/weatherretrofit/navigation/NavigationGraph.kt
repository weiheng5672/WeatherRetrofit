package com.example.weatherretrofit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.weatherretrofit.ui.BarChatScreen
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

            HomeScreen(
                // 如果畫面需要根據不同的 ID 顯示不同的內容，
                // 這樣的字符串模板可以幫助你方便地構造導航路由
                // 在 HomeScreen 中 這個點擊事件 是 各個不同的資訊卡 需要的
                navigateToBarChart = {
                    navController.navigate("${NavigationRoutes.BarDestination.route}/${it}")
                }
            )

        }

        composable(

            route = NavigationRoutes.BarDestination.routeWithArgs,

            arguments = listOf(

                // 這行代碼告訴導航系統
                // 這個 Composable 需要一個名為 itemId 的參數
                navArgument(
                    NavigationRoutes.BarDestination.itemIdArg
                ) {
                    //指定了參數的類型為 Int
                    type = NavType.IntType
                }
            )


        ) {

            BarChatScreen(

                navigateBack = { navController.navigate(NavigationRoutes.HomeDestination.route) },

                )

        }


    }

}