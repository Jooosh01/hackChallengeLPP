package com.onturaa.languagepairingprogram.ui.screens

import android.util.Log
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

            HomeViewModel.Step.Login -> {
                LoginSubScreen(
                    viewModel = viewModel,
                    isSendEnabled = uiState.isSendEnabled,
                    netID = uiState.netID,
                    pass = uiState.password,
                    onNetIDChange = { viewModel.onNetIDChanged(it) },
                    onPasswordChange = { viewModel.onPasswordChanged(it) }
                )
            }

            HomeViewModel.Step.Name -> {
                TextSubScreen(
                    viewModel,
                    uiState.isSendEnabled,
                    HomeViewModel.Step.Name.toString(),
                    uiState.name.orEmpty(),
                    {viewModel.onTextChanged(it)}
                )
            }

            HomeViewModel.Step.NetID -> {
                TextSubScreen(
                    viewModel,
                    uiState.isSendEnabled,
                    HomeViewModel.Step.NetID.toString(),
                    uiState.netID.orEmpty(),
                    {viewModel.onTextChanged(it)}
                )
            }

            HomeViewModel.Step.Year -> {
                TextSubScreen(
                    viewModel,
                    uiState.isSendEnabled,
                    HomeViewModel.Step.Year.toString(),
                    uiState.year.orEmpty(),
                    {viewModel.onTextChanged(it)}
                )
            }

            HomeViewModel.Step.Language -> {
                TextSubScreen(
                    viewModel,
                    uiState.isSendEnabled,
                    HomeViewModel.Step.Language.toString(),
                    uiState.language.orEmpty(),
                    {viewModel.onTextChanged(it)}
                )
            }

            HomeViewModel.Step.Level -> {
                TextSubScreen(
                    viewModel,
                    uiState.isSendEnabled,
                    HomeViewModel.Step.Level.toString(),
                    uiState.level.orEmpty(),
                    {viewModel.onTextChanged(it)}
                )
            }

            HomeViewModel.Step.Bio -> {
                TextSubScreen(
                    viewModel,
                    uiState.isSendEnabled,
                    HomeViewModel.Step.Bio.toString(),
                    uiState.bio.orEmpty(),
                    {viewModel.onTextChanged(it)}
                )
            }

            HomeViewModel.Step.Submit -> {
                SubmitScreen(
                    navController,
                    viewModel,
                    uiState.isSendEnabled,
                )
            }
        }
    }
}

@Composable
private fun WelcomeSubScreen(
    viewModel: HomeViewModel = hiltViewModel()
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
private fun LoginSubScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    isSendEnabled: Boolean,
    netID: String = "",
    pass: String = "",
    onNetIDChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    val uiState by viewModel.uiStateFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LEPBeige)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Login",
            fontSize = 36.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            lineHeight = 44.sp
        )

        Column {
            TextField(
                value = netID,
                placeholder = {
                    Text("netID")
                },
                onValueChange = {
                    onNetIDChange(it)
                },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = pass,
                placeholder = {
                    Text("password")
                },
                onValueChange = {
                    onPasswordChange(it)
                },
                modifier = Modifier.fillMaxWidth()
            )

            // 👇 Error message displayed here if login failed
            if (uiState.loginError != null) {
                Text(
                    text = uiState.loginError!!,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

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
                    viewModel.onSend {
                        if (viewModel.uiStateFlow.value.loginSuccess) {
                            viewModel.onNext()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LEPPurple
                ),
                enabled = isSendEnabled
            ) {
                Text(
                    text = "LOGIN",
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
    viewModel: HomeViewModel = hiltViewModel(),
    isSendEnabled: Boolean,
    placeholder: String = "",
    value: String = "",
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

    HomeButtons(viewModel, isSendEnabled = isSendEnabled)
}

@Composable
private fun SubmitScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
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
                viewModel.onSend {
                    navController.navigate(Screen.PartnerScreen.route)
                }
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
