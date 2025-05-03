package com.onturaa.languagepairingprogram.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavWrapper() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                navController = navController,
            )
        }

        composable(
            route = Screen.PartnerScreen.route,
        ) { backStackEntry ->
            PartnerScreen(navController = navController)
        }

        composable(route = Screen.ChatScreen.route) {
            ChatScreen(
                navController = navController
            )
        }
    }
}