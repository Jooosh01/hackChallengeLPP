package com.onturaa.languagepairingprogram.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    //
) : ViewModel() {

    val uiStateFlow: MutableStateFlow<UiState> = MutableStateFlow(UiState())

    data class UiState(
        val currentStep: Step = Step.Welcome
    )

    enum class Step {
        Welcome,
        LEP,
        Instructions1,
        Instructions2
    }

    fun onNext() {
        uiStateFlow.value = uiStateFlow.value.copy(
            currentStep = when (uiStateFlow.value.currentStep) {
                Step.Welcome -> Step.LEP
                Step.LEP -> Step.Instructions1
                Step.Instructions1 -> Step.Instructions2
                Step.Instructions2 -> Step.Instructions2
            }
        )
    }

    fun onBack() {
        uiStateFlow.value = uiStateFlow.value.copy(
            currentStep = when (uiStateFlow.value.currentStep) {
                Step.Instructions2 -> Step.Instructions1
                Step.Instructions1 -> Step.LEP
                Step.LEP -> Step.Welcome
                Step.Welcome -> Step.Welcome
            }
        )
    }
}