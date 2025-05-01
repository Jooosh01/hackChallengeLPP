package com.onturaa.languagepairingprogram.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
                isSendEnabled = true,
            )
        }

        composable(route = Screen.PartnerScreen.route) {
            PartnerScreen(
                navController = navController
            )
        }

        composable(route = Screen.ChatScreen.route) {
            ChatScreen(
                navController = navController
            )
        }
    }
}