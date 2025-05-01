package com.onturaa.languagepairingprogram.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.onturaa.languagepairingprogram.ui.screens.Screen
import com.onturaa.languagepairingprogram.ui.theme.LEPBeige

@Composable
fun NavBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LEPBeige),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Start Over",
            modifier = Modifier.clickable {
                navController.navigate(Screen.HomeScreen.route)
            }
        )

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Partner Matching",
            modifier = Modifier.clickable {
                navController.navigate(Screen.PartnerScreen.route)
            }
        )

        Icon(
            imageVector = Icons.Default.Email,
            contentDescription = "Chat",
            modifier = Modifier.clickable {
                navController.navigate(Screen.ChatScreen.route)
            }
        )
    }
}

@Preview
@Composable
private fun NavBarPreview() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .background(LEPBeige),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Start Over",
            modifier = Modifier.clickable {
            }
        )

        Spacer(modifier = Modifier.width(28.dp))

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Partner Matching",
            modifier = Modifier.clickable {
            }
        )

        Spacer(modifier = Modifier.width(28.dp))

        Icon(
            imageVector = Icons.Default.Email,
            contentDescription = "Chat",
            modifier = Modifier.clickable {
            }
        )
    }
}