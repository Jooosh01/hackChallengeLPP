package com.onturaa.languagepairingprogram.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.onturaa.languagepairingprogram.ui.theme.LEPPurple

@Composable
fun LanguageCard(
    language: String = "",
    url: String = ""
) {
    Card (modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth()
        .height(125.dp),
        colors = CardColors(
            LEPPurple,
            contentColor = Color.Black,
            disabledContainerColor = LEPPurple,
            disabledContentColor = LEPPurple
        )
    ){
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween){
            Column(modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Text(
                    text = language,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight(), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {

                ImageFromUrl(url)
            }
        }
    }
}

@Composable
fun ImageFromUrl(url: String) {
    AsyncImage(
        model = url,
        contentDescription = "Loaded image",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}