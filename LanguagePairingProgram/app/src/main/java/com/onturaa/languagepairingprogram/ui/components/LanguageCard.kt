package com.onturaa.languagepairingprogram.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.onturaa.languagepairingprogram.model.Language
import com.onturaa.languagepairingprogram.ui.theme.LEPPurple

@Composable
fun LanguageCard(
    language: Language,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() },
        colors = CardColors(
            containerColor = LEPPurple,
            contentColor = Color.Black,
            disabledContainerColor = LEPPurple,
            disabledContentColor = LEPPurple
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = language.name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            ImageFromUrl(language.flagUrl ?: "")
        }
    }
}

@Composable
fun ImageFromUrl(url: String) {
    AsyncImage(
        model = url,
        contentDescription = "Loaded image",
        modifier = Modifier
            .height(60.dp)
    )
}