package com.onturaa.languagepairingprogram.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.onturaa.languagepairingprogram.model.PartnerRepository
import com.onturaa.languagepairingprogram.ui.components.NavBar
import com.onturaa.languagepairingprogram.ui.components.PartnerCard
import com.onturaa.languagepairingprogram.ui.theme.LEPBeige
import com.onturaa.languagepairingprogram.ui.theme.LEPGreen
import com.onturaa.languagepairingprogram.ui.theme.LEPPurple

@Composable
fun PartnerScreen(
    navController: NavController,
    lang: String
) {
    val fakeList: List<PartnerRepository.Partner> = listOf(
        PartnerRepository.Partner("Josh", "jaw542", 2026, "brp", 1),
        PartnerRepository.Partner("Daria", "dib2", 2027, "linguistics", 3),
        PartnerRepository.Partner("John", "dib2", 2028, "linguistics", 4),
        PartnerRepository.Partner("Lilian", "dib2", 2028, "linguistics", 0)

    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LEPBeige)
            .padding(top = 52.dp, bottom = 24.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("TODO LANGUAGE NAME", fontSize = 30.sp, textAlign = TextAlign.Center)
            LazyColumn {
                items(fakeList) { item ->
                    PartnerCard(item, item.targLang )
                }
            }
        }
        Button(
            onClick = {
                navController.navigate(Screen.ChatScreen.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = LEPPurple
            ),
        ) {
            Text(
                text = "Chat",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = LEPBeige
            )
        }

        NavBar(navController)
    }
}

@Composable
private fun PartnerLayout(
    lang: String
) {
    val fakeList: List<PartnerRepository.Partner> = listOf(
        PartnerRepository.Partner("Josh", "jaw542", 2026, "Japanese", 1),
        PartnerRepository.Partner("Daria", "dib2", 2027, "Fortnite", 5),
        PartnerRepository.Partner("John", "dib2", 2028, "linguistics", 4),
        PartnerRepository.Partner("Lilian", "dib2", 2028, "linguistics", 0)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LEPBeige)
            .padding(top = 52.dp, bottom = 24.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("Your Fellow $lang Learners", fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = LEPGreen, textAlign = TextAlign.Center)
            LazyColumn {
                val sorted = fakeList.sortedByDescending { it.selfScore }
                items(sorted) { item ->
                    PartnerCard(item, item.targLang )
                }
            }
        }
        Button(
            onClick = {
//                navController.navigate(Screen.ChatScreen.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = LEPPurple
            ),
        ) {
            Text(
                text = "Chat",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = LEPBeige
            )
        }

//        NavBar(navController)
    }
}

@Preview
@Composable
fun PartnerScreenPreview() {
    PartnerLayout("Japanese")
}