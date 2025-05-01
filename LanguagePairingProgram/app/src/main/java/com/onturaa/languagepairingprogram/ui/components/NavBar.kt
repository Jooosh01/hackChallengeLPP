package com.onturaa.languagepairingprogram.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.onturaa.languagepairingprogram.ui.screens.Screen
import com.onturaa.languagepairingprogram.ui.theme.LEPBeige
import com.onturaa.languagepairingprogram.ui.theme.LEPDarkBlue

@Composable
fun NavBar(
    navController: NavController
) {
    Column {
        Spacer(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .background(Color.DarkGray)
                .height(1.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(LEPBeige)
                .padding(start = 20.dp, end = 20.dp, top = 28.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Start Over",
                    tint = LEPDarkBlue,
                    modifier = Modifier
                        .size(52.dp)
                        .padding(4.dp)
                        .clickable {
                            navController.navigate(Screen.HomeScreen.route)
                        }
                )
            }

            Box(
                modifier = Modifier
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Partner Matching",
                    tint = LEPDarkBlue,
                    modifier = Modifier
                        .size(52.dp)
                        .padding(4.dp)
                        .clickable {
                            navController.navigate(Screen.PartnerScreen.route)
                        }
                )
            }

            Box(
                modifier = Modifier
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Chat",
                    tint = LEPDarkBlue,
                    modifier = Modifier
                        .size(52.dp)
                        .padding(4.dp)
                        .clickable {
                            navController.navigate(Screen.ChatScreen.route)
                        }
                )
            }
        }
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
    }
}