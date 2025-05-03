package com.onturaa.languagepairingprogram.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onturaa.languagepairingprogram.model.PartnerRepository
import com.onturaa.languagepairingprogram.retrofit.ApiService
import com.onturaa.languagepairingprogram.retrofit.RetrofitInstance
import com.onturaa.languagepairingprogram.viewmodel.HomeViewModel.Step
import com.onturaa.languagepairingprogram.viewmodel.HomeViewModel.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PartnerViewModel @Inject constructor(
private val retrofitInstance: RetrofitInstance,
) : ViewModel() {
        private val _uiStateFlow = MutableStateFlow(
            UiState(steps = Step.Welcome)
        )
        val uiStateFlow = _uiStateFlow.asStateFlow()
    private val _selfId = MutableStateFlow<Int?>(null)
    val selfId: StateFlow<Int?> = _selfId.asStateFlow()
        var users by mutableStateOf<List<PartnerRepository.Partner>>(emptyList())
            private set
    fun self () {
        viewModelScope.launch {
        try {
            val allUsers = retrofitInstance.apiService.getAllUsers()
            val name = uiStateFlow.value.name
            val self = allUsers.find { it.name == name }
            if (self != null) {
                _selfId.value = self.id
            } else {
                Log.e("PartnerViewModel", "Self not found in user list")
            }
        } catch (e: Exception) {
            Log.e("PartnerViewModel", "Failed to fetch self: ${e.message}")
        }
    }

    }

        fun getMatches(userId: Int, token: String) {
            viewModelScope.launch {

//                users = apiService.get_matches(userId)
//                users = retrofitInstance.apiService.get_matches(userId)

            }
        }
    }
//}
                users = retrofitInstance.apiService.getMatches(userId)

            }
        }
}
