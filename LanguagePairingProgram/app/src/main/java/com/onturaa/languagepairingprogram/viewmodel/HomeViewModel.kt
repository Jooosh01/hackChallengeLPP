package com.onturaa.languagepairingprogram.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onturaa.languagepairingprogram.model.LoginRequest
import com.onturaa.languagepairingprogram.retrofit.RetrofitInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val retrofitInstance: RetrofitInstance
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(
        UiState(steps = Step.Welcome)
    )
    val uiStateFlow = _uiStateFlow.asStateFlow()

    data class UiState(
        val steps: Step = Step.Welcome,
        val name: String = "",
        val netID: String = "",
        val year: String = "",
        val language: String = "",
        val level: String = "",
        val bio: String = "",
        val password: String = "",
        val loginSuccess: Boolean = false,
        val loginError: String? = null
    ) {
        private fun isValidNetID(): Boolean {
            return netID.matches(Regex("^[a-z]{2,}\\d{2,}$"))
        }

        private fun isValidPassword(): Boolean {
            return password.length >= 5
        }

        val isSendEnabled: Boolean
            get() = when (steps) {
                Step.Login -> isValidNetID() && isValidPassword()
                Step.Name -> name.isNotBlank()
                Step.NetID -> isValidNetID()
                Step.Year -> year.isNotBlank()
                Step.Language -> language.isNotBlank()
                Step.Level -> level.isNotBlank()
                Step.Bio -> bio.isNotBlank()
                Step.Submit -> true
                else -> false
            }
    }

    fun onTextChanged(text: String) {
        val currentState = _uiStateFlow.value
        val updatedState = when (currentState.steps) {
            Step.Name -> currentState.copy(name = text)
            Step.NetID -> currentState.copy(netID = text)
            Step.Year -> currentState.copy(year = text)
            Step.Language -> currentState.copy(language = text)
            Step.Level -> currentState.copy(level = text)
            Step.Bio -> currentState.copy(bio = text)
            else -> currentState
        }

        Log.d("onTextChanged", "Updated state: $updatedState")
        _uiStateFlow.value = updatedState
    }

    fun onNetIDChanged(text: String) {
        val currentState = _uiStateFlow.value
        _uiStateFlow.value = currentState.copy(netID = text)
    }

    fun onPasswordChanged(text: String) {
        val currentState = _uiStateFlow.value
        _uiStateFlow.value = currentState.copy(password = text)
    }

    fun onSend(onSuccess: () -> Unit = {}) {
        val state = _uiStateFlow.value

        when (state.steps) {
            Step.Login -> {
                viewModelScope.launch {
                    try {
                        val loginRequest = LoginRequest(
                            netID = state.netID,
                            password = state.password
                        )
                        val user = retrofitInstance.apiService.login(loginRequest)
                        Log.d("Login", "User logged in: $user")

                        _uiStateFlow.value = state.copy(
                            loginSuccess = true,
                            loginError = null
                        )
                        onSuccess()
                    } catch (e: Exception) {
                        Log.e("LoginError", "Login failed: ${e.message}")

                        _uiStateFlow.value = state.copy(
                            loginSuccess = false,
                            loginError = "Login failed. Please check your credentials."
                        )
                    }
                }
            }

            Step.Submit -> {
                Log.d("Submit", "Submitting user: $state")
                onSuccess()
            }

            else -> {
                onSuccess()
            }
        }
    }


    enum class Step {
        Welcome,
        Login,
        Name,
        NetID,
        Year,
        Language,
        Level,
        Bio,
        Submit
    }

    fun onNext() {
        _uiStateFlow.value = uiStateFlow.value.copy(
            steps = when (uiStateFlow.value.steps) {
                Step.Welcome -> Step.Login
                Step.Login -> Step.Name
                Step.Name -> Step.NetID
                Step.NetID -> Step.Year
                Step.Year -> Step.Language
                Step.Language -> Step.Level
                Step.Level -> Step.Bio
                Step.Bio -> Step.Submit
                Step.Submit -> Step.Submit
            }
        )
    }

    fun onBack() {
        _uiStateFlow.value = uiStateFlow.value.copy(
            steps = when (uiStateFlow.value.steps) {
                Step.Submit -> Step.Bio
                Step.Bio -> Step.Level
                Step.Level -> Step.Language
                Step.Language -> Step.Year
                Step.Year -> Step.NetID
                Step.NetID -> Step.Name
                Step.Name -> Step.Login
                Step.Login -> Step.Welcome
                Step.Welcome -> Step.Welcome
            }
        )
    }
}
