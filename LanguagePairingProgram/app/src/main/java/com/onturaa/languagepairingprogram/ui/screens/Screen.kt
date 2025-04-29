package com.onturaa.languagepairingprogram.ui.screens

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    @Serializable
    data object HomeScreen : Screen("home")

    @Serializable
    data object FormScreen : Screen("form")

    @Serializable
    data object ChatScreen :Screen("chat")
}