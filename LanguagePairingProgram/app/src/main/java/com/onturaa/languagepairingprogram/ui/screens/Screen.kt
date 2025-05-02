package com.onturaa.languagepairingprogram.ui.screens

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    @Serializable
    data object HomeScreen : Screen("home")

    @Serializable
    data object PartnerScreen : Screen("partner/{lang}") {
        fun createRoute(lang: String) = "partner/$lang"
    }

    @Serializable
    data object ChatScreen :Screen("chat")
}