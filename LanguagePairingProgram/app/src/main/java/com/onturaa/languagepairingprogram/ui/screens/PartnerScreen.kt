package com.onturaa.languagepairingprogram.ui.screens

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.onturaa.languagepairingprogram.model.PartnerRepository
import com.onturaa.languagepairingprogram.ui.components.PartnerCard
import com.onturaa.languagepairingprogram.ui.theme.LEPBeige
import com.onturaa.languagepairingprogram.ui.theme.LEPPurple

@Composable
fun PartnerScreen(
    navController: NavController
) {
    val fakeDict: Map<PartnerRepository.Languages, Int> = mapOf(
        PartnerRepository.Languages.Japanese to 5,
        PartnerRepository.Languages.Twi to 1
    )
    val fakeList: List<PartnerRepository.Partner> = listOf(
        PartnerRepository.Partner("Josh", "jaw542", 2026, fakeDict),
        PartnerRepository.Partner("Daria", "dib2", 2027, fakeDict)
    )
    Column {
        Text("TODO LANGUAGE NAME", fontSize = 30.sp, textAlign = TextAlign.Center)
        LazyColumn {
            items(fakeList) { item ->
                PartnerCard(item, PartnerRepository.Languages.Japanese)
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
}

@Composable
private fun PartnerLayout() {
    val fakeDict: Map<PartnerRepository.Languages, Int> = mapOf(
        PartnerRepository.Languages.Japanese to 5,
        PartnerRepository.Languages.Twi to 1
    )
    val fakeList: List<PartnerRepository.Partner> = listOf(
        PartnerRepository.Partner("Josh", "jaw542", 2026, fakeDict),
        PartnerRepository.Partner("Daria", "dib2", 2027, fakeDict)
    )
    Column {
        Text("TODO LANGUAGE NAME", fontSize = 30.sp, textAlign = TextAlign.Center)
        LazyColumn {
            items(fakeList) { item ->
                PartnerCard(item, PartnerRepository.Languages.Japanese)
            }
        }
    }
}

@Preview
@Composable
fun PartnerScreenPreview() {
    PartnerLayout()
}