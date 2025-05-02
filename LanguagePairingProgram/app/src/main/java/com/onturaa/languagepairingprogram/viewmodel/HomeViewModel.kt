package com.onturaa.languagepairingprogram.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        UiState(
            steps = Step.Welcome
        )
    )
    val uiStateFlow = _uiStateFlow.asStateFlow()

    data class UiState(
        val steps: Step = Step.Welcome,
        val userInput: String = "",
        val name: String = "",
        val netID: String = "",
        val year: String = "",
        val language: String = "",
        val level: String = "",
        val bio: String = "",
        val password: String = ""
    ) {
        val isSendEnabled: Boolean
            get() = when (steps) {
                Step.Login -> netID.isNotBlank() && password.isNotBlank()
                Step.Name -> name.isNotBlank()
                Step.NetID -> netID.isNotBlank()
                Step.Year -> year.isNotBlank()
                Step.Language -> language.isNotBlank()
                Step.Level -> level.isNotBlank()
                Step.Bio -> bio.isNotBlank()
                Step.Submit -> true
                else -> userInput.isNotBlank()
            }
    }

    fun onNetIDChanged(text: String) {
        val currentState = _uiStateFlow.value
        _uiStateFlow.value = currentState.copy(netID = text)
    }

    fun onPasswordChanged(text: String) {
        val currentState = _uiStateFlow.value
        _uiStateFlow.value = currentState.copy(password = text)
    }

    fun onTextChanged(text: String) {
        val currentState = _uiStateFlow.value
        Log.d("onTextChanged", "Current userInput: ${currentState.userInput}, New input: $text")

        val updatedState = when (currentState.steps) {
            Step.Name -> currentState.copy(name = text, userInput = text)
            Step.NetID -> currentState.copy(netID = text, userInput = text)
            Step.Year -> currentState.copy(year = text, userInput = text)
            Step.Language -> currentState.copy(language = text, userInput = text)
            Step.Level -> currentState.copy(level = text, userInput = text)
            Step.Bio -> currentState.copy(bio = text, userInput = text)
            else -> currentState.copy(userInput = text)
        }
//        }.copy(userInput = text)

        Log.d("onTextChanged", "Updated state: $updatedState")

        _uiStateFlow.value = updatedState
    }

    fun onSend(onSuccess: () -> Unit = {}) {
        val input = _uiStateFlow.value.userInput

        val updatedState = when (_uiStateFlow.value.steps) {
            Step.Name -> _uiStateFlow.value.copy(name = input)
            Step.NetID -> _uiStateFlow.value.copy(netID = input)
            Step.Year -> _uiStateFlow.value.copy(year = input)
            Step.Language -> _uiStateFlow.value.copy(language = input)
            Step.Level -> _uiStateFlow.value.copy(level = input)
            Step.Bio -> _uiStateFlow.value.copy(bio = input)
            else -> _uiStateFlow.value
        }
        _uiStateFlow.value = updatedState.copy(userInput = "")

        if (_uiStateFlow.value.steps == Step.Submit) {
            val state = _uiStateFlow.value
            viewModelScope.launch {
                try {
                    val user = retrofitInstance.apiService.createUser(
                        netID = state.netID,
                        name = state.name,
                        level = state.level,
                        description = state.bio,
                        language_id = getLanguageIdFromName(state.language),
                        password = state.password
                    )
                    println("User created: $user")
                } catch (e: Exception) {
                    Log.e("NetworkError", "Error creating user: ${e.message}")
                }
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