package com.onturaa.languagepairingprogram.ui.screens

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.onturaa.languagepairingprogram.ui.theme.LEPBeige
import com.onturaa.languagepairingprogram.ui.theme.LEPDarkBlue
import com.onturaa.languagepairingprogram.ui.theme.LEPGreen
import com.onturaa.languagepairingprogram.ui.theme.LEPPink
import com.onturaa.languagepairingprogram.ui.theme.LEPPurple
import com.onturaa.languagepairingprogram.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiStateFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LEPBeige)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState.currentStep) {
            HomeViewModel.Step.Welcome -> {
                Text(
                    text = "WELCOME",
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold,
                    color = LEPGreen
                )

                Spacer(Modifier.height(28.dp))

                Button(
                    onClick = {
                        viewModel.onNext()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LEPPurple
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 4.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "START",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = LEPBeige
                        )

                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "PlayArrow",
                            tint = LEPBeige
                        )
                    }
                }
            }

            HomeViewModel.Step.LEP -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(LEPBeige)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "This is the Language Expansion Program's (LEP) Language Partner Pairing System." +
                                " You will first answer a brief questionnaire and then be prompted to select" +
                                " a language partner to practice with. Then, you will meet up with your partner " +
                                "at LEP's first Language Corners event of the semester. We hope you enjoy!"
                    )
                    Text(
                        text = "Welcome to the Language Expansion Program's"
                    )
                    Text(
                        text = "Language Partner Pairing",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = LEPDarkBlue,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(28.dp))

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
            }

            HomeViewModel.Step.Instructions1 -> {

            }

            HomeViewModel.Step.Instructions2 -> {

            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}