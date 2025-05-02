package com.onturaa.languagepairingprogram.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.onturaa.languagepairingprogram.ui.theme.LEPBeige
import com.onturaa.languagepairingprogram.ui.theme.LEPPurple
import com.onturaa.languagepairingprogram.viewmodel.HomeViewModel

@Composable
fun HomeButtons(
    viewModel: HomeViewModel,
    isSendEnabled: Boolean?
) {
    Row {
        Button(
            onClick = {
                viewModel.onBack()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = LEPPurple
            )
        ) {
            Text(
                text = "BACK",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = LEPBeige
            )
        }

        Spacer(Modifier.width(28.dp))

        Button(
            onClick = {
                viewModel.onNext()
            },
            enabled = isSendEnabled == true,
            colors = ButtonDefaults.buttonColors(
                containerColor = LEPPurple
            )
        ) {
            Text(
                text = "NEXT",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = LEPBeige
            )
        }
    }
}