package com.onturaa.languagepairingprogram.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onturaa.languagepairingprogram.model.Language
import com.onturaa.languagepairingprogram.model.UserRequest
import com.onturaa.languagepairingprogram.retrofit.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: ApiService
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
        val languageId: Int = -1,
        val bio: String = "",
        val sendSuccess: Boolean = false,
        val sendError: String? = null
    ) {
        private fun isValidNetID(): Boolean {
            return netID.matches(Regex("^[a-z]{1,}\\d{1,}$"))
        }

        private fun isValidPassword(): Boolean {
            return password.length >= 5
        }

        val isSendEnabled: Boolean
            get() = when (steps) {
                Step.NetID -> isValidNetID()
                Step.Name -> name.isNotBlank()
                Step.Password -> isValidPassword()
                Step.Language -> language.isNotBlank()
                Step.Level -> level.isNotBlank()
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
            Step.Language -> currentState.copy(language = text)
            Step.Level -> currentState.copy(level = text)
            Step.Description -> currentState.copy(bio = text)
            else -> currentState
        }

        Log.d("onTextChanged", "Updated state: $updatedState")
        _uiStateFlow.value = updatedState
    }

    fun onSend(onSuccess: () -> Unit = {}) {
        val state = _uiStateFlow.value

        when (state.steps) {
            Step.Submit -> {
                viewModelScope.launch {
                    try {
                        val userRequest = UserRequest(
                            netID = state.netID,
                            name = state.name,
                            password = state.password,
                            level = state.level,
                            languageId = 1,
                            description = state.bio
                        )

                        val user = apiService.createUser(userRequest)

                        _uiStateFlow.value = state.copy(
                            sendSuccess = true,
                            sendError = null
                        )

                        onSuccess()
                    } catch (e: Exception) {

                        _uiStateFlow.value = state.copy(
                            sendSuccess = false,
                            sendError = "User creation failed. Please try again."
                        )
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
        NetID,
        Name,
        Password,
        Language,
        Level,
        Description,
        Submit
    }

    fun onNext() {
        _uiStateFlow.value = _uiStateFlow.value.copy(
            steps = when (_uiStateFlow.value.steps) {
                Step.Welcome -> Step.NetID
                Step.NetID -> Step.Name
                Step.Name -> Step.Password
                Step.Password -> Step.Language
                Step.Language -> Step.Level
                Step.Level -> Step.Description
                Step.Description -> Step.Submit
                Step.Submit -> Step.Submit
            }
        )
    }


    fun onBack() {
        _uiStateFlow.value = _uiStateFlow.value.copy(
            steps = when (_uiStateFlow.value.steps) {
                Step.Submit -> Step.Description
                Step.Description -> Step.Level
                Step.Level -> Step.Language
                Step.Language -> Step.Password
                Step.Password -> Step.Name
                Step.Name -> Step.NetID
                Step.NetID -> Step.Welcome
                Step.Welcome -> Step.Welcome
            }
        )
    }

    private val _languages = MutableStateFlow<List<Language>>(emptyList())
    val languages = _languages.asStateFlow()

    fun loadLanguages() {
        viewModelScope.launch {
            try {
                val response = apiService.getLanguages()
                _languages.value = response
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Failed to fetch languages, using fallback", e)
                _languages.value = listOf(
                    Language(
                        id = 1,
                        name = "English",
                        flagUrl = "https://lpphack.s3.us-east-2.amazonaws.com/english.png"
                    ),
                    Language(
                        id = 2,
                        name = "Spanish",
                        flagUrl = "https://lpphack.s3.us-east-2.amazonaws.com/spanish.png"
                    ),
                    Language(
                        id = 3,
                        name = "Mandarin",
                        flagUrl = "https://lpphack.s3.us-east-2.amazonaws.com/mandarin.png"
                    ),
                    Language(
                        id = 4,
                        name = "Polish ",
                        flagUrl = "https://lpphack.s3.us-east-2.amazonaws.com/polish.png"
                    ),
                    Language(
                        id = 5,
                        name = "Twi",
                        flagUrl = "https://lpphack.s3.us-east-2.amazonaws.com/twi.png"
                    ),
                    Language(
                        id = 1,
                        name = "English",
                        flagUrl = "https://lpphack.s3.us-east-2.amazonaws.com/english.png"
                    ),
                    Language(
                        id = 2,
                        name = "Spanish",
                        flagUrl = "https://lpphack.s3.us-east-2.amazonaws.com/spanish.png"
                    ),
                    Language(
                        id = 3,
                        name = "Mandarin",
                        flagUrl = "https://lpphack.s3.us-east-2.amazonaws.com/mandarin.png"
                    ),
                    Language(
                        id = 4,
                        name = "Polish ",
                        flagUrl = "https://lpphack.s3.us-east-2.amazonaws.com/polish.png"
                    ),
                    Language(
                        id = 5,
                        name = "Twi",
                        flagUrl = "https://lpphack.s3.us-east-2.amazonaws.com/twi.png"
                    )
                )
            }
        }
    }


    fun onLanguageSelected(language: Language) {
        _uiStateFlow.value = _uiStateFlow.value.copy(
            language = language.name,
            languageId = language.id
        )
    }

}
