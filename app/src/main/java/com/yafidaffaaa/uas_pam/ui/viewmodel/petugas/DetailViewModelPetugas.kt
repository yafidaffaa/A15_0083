package com.yafidaffaaa.uas_pam.ui.viewmodel.petugas

import com.yafidaffaaa.uas_pam.model.Petugas
import com.yafidaffaaa.uas_pam.repository.PetugasRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class DetailViewModelPetugas(private val ptgRepository: PetugasRepository) : ViewModel() {

    var uiState by mutableStateOf(DetailUiState())
        private set

    fun DetailPetugas(ID: String) {
        viewModelScope.launch {
            uiState = DetailUiState(isLoading = true)
            try {
                val petugas =
                    ptgRepository.getPetugasById(ID)
                uiState = DetailUiState(detailUiEvent = petugas!!.toDetailUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = DetailUiState(
                    isError = true,
                    errorMessage = "Failed to fetch details: ${e.message}"
                )
            }
        }

    }

    fun deletePetugasByID(id: String) {
        viewModelScope.launch {
            try {
                ptgRepository.deletePetugas(id)
            } catch (e: IOException) {
                HomeUiState.Error("Kesalahan jaringan: ${e.message}")
            } catch (e: HttpException) {
                HomeUiState.Error("Kesalahan server: ${e.message}")
            }
        }
    }
}

data class DetailUiState(
    val detailUiEvent: InsertUiEvent = InsertUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertUiEvent()
}

fun Petugas.toDetailUiEvent(): InsertUiEvent {
    return InsertUiEvent(
        id_petugas = id_petugas.toString(),
        nama_petugas = nama_petugas,
        jabatan = jabatan,
    )
}