package com.onturaa.languagepairingprogram.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.onturaa.languagepairingprogram.model.PartnerRepository
import com.onturaa.languagepairingprogram.ui.theme.LEPBeige
import com.onturaa.languagepairingprogram.ui.theme.LEPPurple

fun  ratingToString(sr: Int): String{
    return when (sr){
        1 -> "Beginner"
        2-> "Intermediate"
        3 -> "High Intermediate"
        4 -> "Advanced"
        5-> "Native"
        else -> "DONT PASS IN LESS THAN 1 or GREATER THAN 5"
    }
}

@Composable
fun PartnerCard (partner: PartnerRepository.Partner, lang: String) {
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
                .fillMaxHeight(), verticalArrangement = Arrangement.Top) {
                Text(text = partner.n,  fontSize = 30.sp,
                    fontWeight = FontWeight.Bold)
                Text(partner.netId, fontSize = 15.sp)
                Text(partner.year.toString())
            }
            Column(modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight(), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Bottom) {

                Text(ratingToString(partner.selfScore))
            }
        }
    }
}
@Composable
@Preview
fun PartnerCardPreview(){
    val fakePartner = PartnerRepository.Partner("Josh", "jaw542",2026, "Japanese", 5)
    PartnerCard(fakePartner, "Japanese")
}

