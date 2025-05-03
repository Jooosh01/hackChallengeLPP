package com.onturaa.languagepairingprogram.ui.screens

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.onturaa.languagepairingprogram.ui.components.NavBar
import com.onturaa.languagepairingprogram.ui.theme.LEPBeige
import com.onturaa.languagepairingprogram.ui.theme.LEPBlue
import com.onturaa.languagepairingprogram.ui.theme.LEPDarkBlue
import com.onturaa.languagepairingprogram.ui.theme.LEPGreen
import com.onturaa.languagepairingprogram.ui.theme.LEPPurple

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
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Chatroom",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = LEPGreen,
                textAlign = TextAlign.Center,
                lineHeight = 54.sp,
            )

            Spacer(Modifier.height(6.dp))

            Text(
                "Josh",
                fontSize = 25.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 54.sp,
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                colors = CardColors(
                    LEPBlue,
                    contentColor = Color.White,
                    disabledContainerColor = LEPPurple,
                    disabledContentColor = LEPPurple
                )
            ) {
                Text(modifier = Modifier
                    .padding(12.dp),
                    text = "Cześć, jak się masz?")
            }
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                colors = CardColors(
                    LEPPurple,
                    contentColor = Color.White,
                    disabledContainerColor = LEPPurple,
                    disabledContentColor = LEPPurple
                )
            ) {
                Text(modifier = Modifier
                    .padding(12.dp),
                    text = "Wszystko dobrze!",
                    textAlign = TextAlign.End)
            }
            NavBar(navController)
        }
    }
}