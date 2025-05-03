package com.onturaa.languagepairingprogram.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onturaa.languagepairingprogram.model.LoginRequest
import com.onturaa.languagepairingprogram.model.User
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
        val password: String = "",
        val level: String = "",
        val language: String = "",
        val bio: String = "",
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
                Step.Login -> true // Just to proceed to NetID screen
                Step.NetID -> isValidNetID()
                Step.Name -> name.isNotBlank()
                Step.Password -> isValidPassword()
                Step.Level -> level.isNotBlank()
                Step.Language -> language.isNotBlank()
                Step.Description -> bio.isNotBlank()
                Step.Submit -> true
                else -> false
            }
    }

    fun onTextChanged(text: String) {
        val currentState = _uiStateFlow.value
        val updatedState = when (currentState.steps) {
            Step.Name -> currentState.copy(name = text)
            Step.NetID -> currentState.copy(netID = text)
            Step.Password -> currentState.copy(password = text)
            Step.Level -> currentState.copy(level = text)
            Step.Language -> currentState.copy(language = text)
            Step.Description -> currentState.copy(bio = text)
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
                // Just go forward on login screen, no server call
                Log.d("HomeViewModel", "Login step - No action, proceeding.")
                onSuccess()
            }

            Step.Submit -> {
                viewModelScope.launch {
                    try {
                        // Create user with the data from the UI state
                        val user = retrofitInstance.apiService.createUser(
                            netID = state.netID,
                            name = state.name,
                            password = state.password,
                            level = state.level,
                            languageId = state.language,
                            description = state.bio
                        )

                        Log.d("User Creation", "User created: $user")

                        // If successful, update the state and call the onSuccess callback to navigate
                        _uiStateFlow.value = state.copy(
                            loginSuccess = true,
                            loginError = null
                        )

                        // Ensure we have successfully updated the state
                        Log.d("HomeViewModel", "State updated successfully, loginSuccess = true")

                        // Call onSuccess to trigger the navigation
                        onSuccess()

                    } catch (e: Exception) {
                        Log.e("User Creation Error", "User creation failed: ${e.message}")

                        // Handle errors
                        _uiStateFlow.value = state.copy(
                            loginSuccess = false,
                            loginError = "User creation failed. Please try again."
                        )

                        Log.d("HomeViewModel", "User creation failed, loginSuccess = false")
                    }
                }
            }

            else -> {
                onSuccess()
            }
        }
    }



    enum class Step {
        Welcome,
        Login,
        NetID,
        Name,
        Password,
        Level,
        Language,
        Description,
        Submit
    }

    fun onNext() {
        _uiStateFlow.value = _uiStateFlow.value.copy(
            steps = when (_uiStateFlow.value.steps) {
                Step.Welcome -> Step.Login
                Step.Login -> Step.NetID
                Step.NetID -> Step.Name
                Step.Name -> Step.Password
                Step.Password -> Step.Level
                Step.Level -> Step.Language
                Step.Language -> Step.Description
                Step.Description -> Step.Submit
                Step.Submit -> Step.Submit
            }
        )
    }

    fun onBack() {
        _uiStateFlow.value = _uiStateFlow.value.copy(
            steps = when (_uiStateFlow.value.steps) {
                Step.Submit -> Step.Description
                Step.Description -> Step.Language
                Step.Language -> Step.Level
                Step.Level -> Step.Password
                Step.Password -> Step.Name
                Step.Name -> Step.NetID
                Step.NetID -> Step.Login
                Step.Login -> Step.Welcome
                Step.Welcome -> Step.Welcome
            }
        )
    }
}
