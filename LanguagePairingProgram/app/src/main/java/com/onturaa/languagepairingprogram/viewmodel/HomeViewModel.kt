package com.onturaa.languagepairingprogram.viewmodel

import androidx.lifecycle.ViewModel
import com.onturaa.languagepairingprogram.retrofit.RetrofitInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        val level: String = ""
    ) {
        val isSendEnabled: Boolean
            get() = userInput.isNotBlank()
    }

    fun onTextChanged(text: String) {
        val currentState = _uiStateFlow.value
        val updatedState = when (currentState.steps) {
            Step.Name -> currentState.copy(name = text)
            Step.NetID -> currentState.copy(netID = text)
            Step.Year -> currentState.copy(year = text)
            Step.Language -> currentState.copy(language = text)
            Step.Level -> currentState.copy(level = text)
            else -> currentState
        }
        _uiStateFlow.value = updatedState
//        _uiStateFlow.value = _uiStateFlow.value.copy(name = text)
    }

    fun onSend() {
//        userRepository.sendQuestion(input)
        val input = _uiStateFlow.value.userInput
        when (_uiStateFlow.value.steps) {
            Step.Name -> _uiStateFlow.value.copy(name = input)
            Step.NetID -> _uiStateFlow.value.copy(netID = input)
            Step.Year -> _uiStateFlow.value.copy(year = input)
            Step.Language -> _uiStateFlow.value.copy(language = input)
            Step.Level -> _uiStateFlow.value.copy(level = input)
            else -> _uiStateFlow.value
        }
        _uiStateFlow.value = _uiStateFlow.value.copy(userInput = "")
    }

    enum class Step {
        Welcome,
        LEP,
        Name,
        NetID,
        Year,
        Language,
        Level
    }

    fun onNext() {
        _uiStateFlow.value = uiStateFlow.value.copy(
            steps = when (uiStateFlow.value.steps) {
                Step.Welcome -> Step.LEP
                Step.LEP -> Step.Name
                Step.Name -> Step.NetID
                Step.NetID -> Step.Year
                Step.Year -> Step.Language
                Step.Language -> Step.Level
                Step.Level -> Step.Level
            }
        )
    }

    fun onBack() {
        _uiStateFlow.value = uiStateFlow.value.copy(
            steps = when (uiStateFlow.value.steps) {
                Step.Level -> Step.Language
                Step.Language -> Step.Year
                Step.Year -> Step.NetID
                Step.NetID -> Step.Name
                Step.Name -> Step.LEP
                Step.LEP -> Step.Welcome
                Step.Welcome -> Step.Welcome
            }
        )
    }

//    init {
//        viewModelScope.launch {
//            userRepository.userQuestionFlow.collect { questions ->
//                _uiStateFlow.value = _uiStateFlow.value.copy(questions = questions)
//            }
//        }
//    }
}