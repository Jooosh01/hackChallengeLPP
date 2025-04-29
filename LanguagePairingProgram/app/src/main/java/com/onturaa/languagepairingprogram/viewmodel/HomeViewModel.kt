package com.onturaa.languagepairingprogram.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.Language
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    //
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
        _uiStateFlow.value = _uiStateFlow.value.copy(userInput = text)
    }

    fun onSend() {
        val question = _uiStateFlow.value.userInput
//        userRepository.sendQuestion(question)
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