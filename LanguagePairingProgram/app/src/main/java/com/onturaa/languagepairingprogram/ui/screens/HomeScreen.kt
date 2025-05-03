package com.onturaa.languagepairingprogram.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.onturaa.languagepairingprogram.ui.components.HomeButtons
import com.onturaa.languagepairingprogram.ui.theme.LEPBeige
import com.onturaa.languagepairingprogram.ui.theme.LEPGreen
import com.onturaa.languagepairingprogram.ui.theme.LEPPurple
import com.onturaa.languagepairingprogram.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiStateFlow.collectAsState()
    val currentStep = uiState.steps

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LEPBeige)
            .padding(top = 52.dp, bottom = 24.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (currentStep) {
            HomeViewModel.Step.Welcome -> {
                WelcomeSubScreen(viewModel)
            }

            HomeViewModel.Step.NetID -> {
                TextSubScreen(
                    viewModel = viewModel,
                    isSendEnabled = uiState.isSendEnabled,
                    placeholder = HomeViewModel.Step.NetID.toString(),
                    value = uiState.netID,
                    onValueChange = { viewModel.onTextChanged(it) }
                )
            }

            HomeViewModel.Step.Name -> {
                TextSubScreen(
                    viewModel = viewModel,
                    isSendEnabled = uiState.isSendEnabled,
                    placeholder = HomeViewModel.Step.Name.toString(),
                    value = uiState.name,
                    onValueChange = { viewModel.onTextChanged(it) }
                )
            }

            HomeViewModel.Step.Password -> {
                TextSubScreen(
                    viewModel = viewModel,
                    isSendEnabled = uiState.isSendEnabled,
                    placeholder = HomeViewModel.Step.Password.toString(),
                    value = uiState.password,
                    onValueChange = { viewModel.onTextChanged(it) }
                )
            }

            HomeViewModel.Step.Level -> {
                TextSubScreen(
                    viewModel = viewModel,
                    isSendEnabled = uiState.isSendEnabled,
                    placeholder = HomeViewModel.Step.Level.toString(),
                    value = uiState.level,
                    onValueChange = { viewModel.onTextChanged(it) }
                )
            }

            HomeViewModel.Step.Language -> {
                TextSubScreen(
                    viewModel = viewModel,
                    isSendEnabled = uiState.isSendEnabled,
                    placeholder = HomeViewModel.Step.Language.toString(),
                    value = uiState.language,
                    onValueChange = { viewModel.onTextChanged(it) }
                )
            }

            HomeViewModel.Step.Description -> {
                TextSubScreen(
                    viewModel = viewModel,
                    isSendEnabled = uiState.isSendEnabled,
                    placeholder = HomeViewModel.Step.Description.toString(),
                    value = uiState.bio,
                    onValueChange = { viewModel.onTextChanged(it) }
                )
            }

            HomeViewModel.Step.Submit -> {
                SubmitScreen(
                    navController = navController,
                    viewModel = viewModel,
                    isSendEnabled = uiState.isSendEnabled,
                )
            }
        }
    }
}

@Composable
private fun SubmitScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    isSendEnabled: Boolean,
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
                navController.navigate(Screen.PartnerScreen.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = LEPPurple
            ),
            enabled = isSendEnabled
        ) {
            Text(
                text = "SUBMIT",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = LEPBeige
            )
        }
    }
}

@Composable
private fun WelcomeSubScreen(viewModel: HomeViewModel) {
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
private fun TextSubScreen(
    viewModel: HomeViewModel,
    isSendEnabled: Boolean,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit
) {
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
            value = value,
            placeholder = {
                Text(placeholder)
            },
            onValueChange = {
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }

    Spacer(modifier = Modifier.height(28.dp))

    HomeButtons(viewModel = viewModel, isSendEnabled = isSendEnabled)
}
