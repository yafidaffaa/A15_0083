package com.yafidaffaaa.uas_pam.ui.viewmodel.petugas

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.yafidaffaaa.uas_pam.model.Petugas
import com.yafidaffaaa.uas_pam.repository.PetugasRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeUiState {
    data class Success(val petugas: List<Petugas>) : HomeUiState()
    object Loading : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModelPetugas(private val ptg: PetugasRepository) : ViewModel() {
    var ptgUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getPetugas()
    }

    fun getPetugas() {
        viewModelScope.launch {
            ptgUIState = HomeUiState.Loading
            ptgUIState = try {
                HomeUiState.Success(ptg.getPetugas())
            } catch (e: IOException) {
                Log.e("PetugasViewModel", "Kesalahan jaringan: ${e.message}", e)
                HomeUiState.Error("Kesalahan jaringan: ${e.message}")
            } catch (e: HttpException) {
                Log.e("PetugasViewModel", "Kesalahan server: ${e.message}", e)
                HomeUiState.Error("Kesalahan server: ${e.message}")
            } catch (e: Exception) {
                Log.e("PetugasViewModel", "Terjadi kesalahan tak terduga: ${e.message}", e)
                HomeUiState.Error("Terjadi kesalahan tak terduga: ${e.message}")
            }
        }
    }

    fun searchPetugas(query: String) {
        ptgUIState = HomeUiState.Loading
        viewModelScope.launch {
            try {
                val result = ptg.searchPetugas(query)

                if (result.isNotEmpty()) {
                    ptgUIState = HomeUiState.Success(result)
                } else {
                    ptgUIState = HomeUiState.Error("No matching officers found")
                }
            } catch (e: Exception) {
                Log.e("PetugasViewModel", "Error searching officer: ${e.message}", e)
                ptgUIState = HomeUiState.Error("An error occurred: ${e.message}")
            }
        }
    }
}