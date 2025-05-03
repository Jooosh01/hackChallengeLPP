package com.onturaa.languagepairingprogram.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onturaa.languagepairingprogram.model.PartnerRepository
import com.onturaa.languagepairingprogram.retrofit.ApiService
import com.onturaa.languagepairingprogram.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import javax.inject.Inject

class PartnerViewModel @Inject constructor(
private val retrofitInstance: RetrofitInstance,
) : ViewModel(){
    class PartnerViewModel(
        private val apiService: ApiService
    ) : ViewModel() {

        var users by mutableStateOf<List<PartnerRepository.Partner>>(emptyList())
            private set


        fun getMatches(userId: Int, token: String) {
            viewModelScope.launch {

//                users = apiService.get_matches(userId)

            }
        }
    }
}