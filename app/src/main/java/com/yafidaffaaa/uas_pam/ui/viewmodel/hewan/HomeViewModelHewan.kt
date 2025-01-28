package com.yafidaffaaa.uas_pam.ui.viewmodel.hewan

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.yafidaffaaa.uas_pam.model.Hewan
import com.yafidaffaaa.uas_pam.repository.HewanRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeUiState {
    data class Success(val hewan: List<Hewan>) : HomeUiState()
    object Loading : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModelHewan(private val hwn: HewanRepository) : ViewModel() {
    var hwnUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getHewan()
    }

    fun getHewan() {
        viewModelScope.launch {
            hwnUIState = HomeUiState.Loading
            hwnUIState = try {
                HomeUiState.Success(hwn.getHewan())
            } catch (e: IOException) {
                Log.e("HewanViewModel", "Kesalahan jaringan: ${e.message}", e)
                HomeUiState.Error("Kesalahan jaringan: ${e.message}")
            } catch (e: HttpException) {
                Log.e("HewanViewModel", "Kesalahan server: ${e.message}", e)
                HomeUiState.Error("Kesalahan server: ${e.message}")
            } catch (e: Exception) {
                Log.e("HewanViewModel", "Terjadi kesalahan tak terduga: ${e.message}", e)
                HomeUiState.Error("Terjadi kesalahan tak terduga: ${e.message}")
            }
        }
    }

    fun searchHewan(query: String) {
        hwnUIState = HomeUiState.Loading
        viewModelScope.launch {
            try {
                val result = hwn.searchHewan(query)

                if (result.isNotEmpty()) {
                    hwnUIState = HomeUiState.Success(result)
                } else {
                    hwnUIState = HomeUiState.Error("No matching animals found")
                }
            } catch (e: Exception) {
                Log.e("HewanViewModel", "Error searching animal: ${e.message}", e)
                hwnUIState = HomeUiState.Error("An error occurred: ${e.message}")
            }
        }
    }
}