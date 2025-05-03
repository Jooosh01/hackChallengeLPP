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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.onturaa.languagepairingprogram.model.PartnerRepository
import com.onturaa.languagepairingprogram.ui.components.NavBar
import com.onturaa.languagepairingprogram.ui.components.PartnerCard
import com.onturaa.languagepairingprogram.ui.theme.LEPBeige
import com.onturaa.languagepairingprogram.ui.theme.LEPGreen
import com.onturaa.languagepairingprogram.ui.theme.LEPPurple
import com.onturaa.languagepairingprogram.viewmodel.HomeViewModel
import com.onturaa.languagepairingprogram.viewmodel.PartnerViewModel

@Composable
fun PartnerScreen(
    navController: NavController,
) {
    val fakeList: List<PartnerRepository.Partner> = listOf(
        PartnerRepository.Partner(3, "jaw542", "Josh", "Advanced", false, null, PartnerRepository.Language(
            3,
            "Japanese",
            "https://lpphack.s3.us-east-2.amazonaws.com/japanese.png")),
        PartnerRepository.Partner(2, "dib42", "Daria", "Beginner", false, null, PartnerRepository.Language(
            2,
            "Japanese",
            "https://lpphack.s3.us-east-2.amazonaws.com/japanese.png")),
        PartnerRepository.Partner(1, "ja007", "John", "Intermediate", false, null, PartnerRepository.Language(
            3,
            "Japanese",
            "https://lpphack.s3.us-east-2.amazonaws.com/japanese.png")),
        PartnerRepository.Partner(4, "lw555", "Lillian", "Beginner", false, null, PartnerRepository.Language(
            3,
            "Japanese",
            "https://lpphack.s3.us-east-2.amazonaws.com/japanese.png")),
        PartnerRepository.Partner(5, "su24", "Sasuke", "Native", false, null, PartnerRepository.Language(
            3,
            "Japanese",
            "https://lpphack.s3.us-east-2.amazonaws.com/japanese.png"))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LEPBeige)
            .padding(top = 52.dp, bottom = 24.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Your Fellow Japanese Learners", fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = LEPGreen, textAlign = TextAlign.Center, lineHeight = 54.sp,
            modifier = Modifier.padding(vertical = 25.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(fakeList) { item ->
                PartnerCard(item, item.language.name)
            }
        }

        Button(
            onClick = {
                navController.navigate(Screen.ChatScreen.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = LEPPurple
            ),
            modifier = Modifier.padding(vertical = 25.dp)
        ) {
            Text(
                text = "Chat",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = LEPBeige
            )
        }
    }
}

@Composable
private fun Partner(
    navController: NavController,
    lang: String
) {
    val fakeList: List<PartnerRepository.Partner> = listOf(
        PartnerRepository.Partner(3, "jaw542", "Josh", "Beginner", false, null, PartnerRepository.Language(
            3,
            "Japanse",
            "val flag_url: String"))
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
                    PartnerCard(item, item.language.name )
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
    // navController: NavController,
    //viewModel: PartnerViewModel,
    user: PartnerRepository.Partner,
    //token: String
) {

    val fakeList: List<PartnerRepository.Partner> = listOf(
        PartnerRepository.Partner(3, "jaw542", "Josh", "Beginner", false, null, PartnerRepository.Language(
            3,
            "Japanese",
            "https://lpphack.s3.us-east-2.amazonaws.com/japanese.png")) ,
        PartnerRepository.Partner(2, "dib42", "Daria", "Intermediate", false, null, PartnerRepository.Language(
            2,
            "Japanese",
            "https://lpphack.s3.us-east-2.amazonaws.com/japanese.png")),
        PartnerRepository.Partner(1, "jaw542", "John", "High Intermediate", false, null, PartnerRepository.Language(
            3,
            "Japanese",
            "https://lpphack.s3.us-east-2.amazonaws.com/japanese.png")),
        PartnerRepository.Partner(4, "jaw542", "Lilian", "Beginner", false, null, PartnerRepository.Language(
            3,
            "Japanese",
            "https://lpphack.s3.us-east-2.amazonaws.com/japanese.png"))

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
            Text("Your Fellow ${user.language.name} Learners", fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = LEPGreen, textAlign = TextAlign.Center)
            LazyColumn {
                items(fakeList) { item ->
                    PartnerCard(item, item.language.name )
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
   val fakePart = PartnerRepository.Partner(3, "jaw542", "Josh", "Beginner", false, null, PartnerRepository.Language(
        3,
        "Japanese",
        "https://lpphack.s3.us-east-2.amazonaws.com/japanese.png"))
    PartnerLayout(fakePart)
}