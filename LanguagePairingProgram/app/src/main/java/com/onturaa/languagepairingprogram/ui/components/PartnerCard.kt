package com.onturaa.languagepairingprogram.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.onturaa.languagepairingprogram.model.PartnerRepository

@Composable
fun PartnerCard (partner: PartnerRepository.Partner, lang: PartnerRepository.Languages) {
    Card {
        Column (modifier = Modifier .fillMaxWidth()){
            Text(partner.n)
            Text(partner.netId)
        }
        Column { Text(partner.year.toString())
            Text("Self Rating " + partner.languages.get(lang))
        }
    }
}
@Composable
@Preview
fun PartnerCardPreview(){
    val fakeDict : Map<PartnerRepository.Languages, Int> = mapOf(
    PartnerRepository.Languages.Japanese to 5,
        PartnerRepository.Languages.Twi to 1
    )
    val fakePartner = PartnerRepository.Partner("Josh", "jaw542",2026, fakeDict)
    PartnerCard(fakePartner, PartnerRepository.Languages.Japanese)
}

