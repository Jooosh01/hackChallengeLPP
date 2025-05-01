package com.onturaa.languagepairingprogram.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.onturaa.languagepairingprogram.ui.theme.LEPBeige
import com.onturaa.languagepairingprogram.ui.theme.LEPDarkBlue
import com.onturaa.languagepairingprogram.ui.theme.LEPGreen
import com.onturaa.languagepairingprogram.ui.theme.LEPOrange
import com.onturaa.languagepairingprogram.ui.theme.LEPPurple
import com.onturaa.languagepairingprogram.viewmodel.HomeViewModel
import org.intellij.lang.annotations.JdkConstants

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
    isSendEnabled: Boolean,
) {
    val uiState by viewModel.uiStateFlow.collectAsState()
    val states = uiState.steps

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LEPBeige)
            .padding(top = 52.dp, bottom = 24.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (states) {
            HomeViewModel.Step.Welcome -> {
                WelcomeSubScreen(viewModel)
            }

            HomeViewModel.Step.LEP -> {
                LEPSubScreen(viewModel)
            }

            HomeViewModel.Step.Name -> {
                TextSubScreen(
                    viewModel, isSendEnabled, "Walker White"
                )
            }

            HomeViewModel.Step.NetID -> {
                TextSubScreen(
                    viewModel, isSendEnabled, "ww123"
                )
            }

            HomeViewModel.Step.Year -> {
                TextSubScreen(
                    viewModel, isSendEnabled, "2026"
                )
            }

            HomeViewModel.Step.Language -> {
                TextSubScreen(
                    viewModel, isSendEnabled, "C++"
                )
            }

            HomeViewModel.Step.Level -> {
                EndSubScreen(
                    navController, viewModel, isSendEnabled, "Native Speaker"
                )
            }
        }
    }
}

@Composable
private fun WelcomeSubScreen(
    viewModel: HomeViewModel = viewModel()
) {
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

@Composable
private fun LEPSubScreen(
    viewModel: HomeViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LEPBeige)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = " ",
            fontSize = 30.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            lineHeight = 36.sp
        )

        Spacer(Modifier.height(14.dp))

        Text(
            text = "Fill out the partner selection form to get started!",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            lineHeight = 44.sp
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

@Composable
private fun TextSubScreen(
    viewModel: HomeViewModel = viewModel(),
    isSendEnabled: Boolean,
    placeholder: String = ""
) {
    val uiState by viewModel.uiStateFlow.collectAsState()

    Box(
        modifier = Modifier
            .background(
                Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = uiState.userInput,
            placeholder = {
                Text(placeholder)
            },
            onValueChange = {
                viewModel.onTextChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }

    Spacer(modifier = Modifier.height(28.dp))

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
                viewModel.onSend()
                viewModel.onNext()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = LEPPurple
            ),
            enabled = isSendEnabled
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

@Composable
private fun EndSubScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
    isSendEnabled: Boolean,
    placeholder: String = ""
) {
    val uiState by viewModel.uiStateFlow.collectAsState()

    Box(
        modifier = Modifier
            .background(
                Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = uiState.userInput,
            placeholder = {
                Text(placeholder)
            },
            onValueChange = {
                viewModel.onTextChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }

    Spacer(modifier = Modifier.height(28.dp))

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
                viewModel.onSend()
                navController.navigate(Screen.PartnerScreen.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = LEPPurple
            ),
            enabled = isSendEnabled
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

@Preview
@Composable
private fun HomeScreenPreview() {
    TextSubScreen(
        viewModel(),
        true,
        "text here"
    )
}