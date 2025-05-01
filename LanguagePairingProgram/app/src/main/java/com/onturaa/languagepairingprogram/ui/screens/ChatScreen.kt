package com.onturaa.languagepairingprogram.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.onturaa.languagepairingprogram.ui.components.NavBar
import com.onturaa.languagepairingprogram.ui.theme.LEPBeige

@Composable
fun ChatScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LEPBeige)
            .padding(top = 52.dp, bottom = 24.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        NavBar(navController)
    }
}