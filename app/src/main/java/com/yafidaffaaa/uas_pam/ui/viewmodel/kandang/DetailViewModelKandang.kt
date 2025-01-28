package com.yafidaffaaa.uas_pam.ui.viewmodel.kandang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yafidaffaaa.uas_pam.model.Kandang
import com.yafidaffaaa.uas_pam.repository.KandangRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class DetailViewModelKandang(private val kandangRepository: KandangRepository) : ViewModel() {

    var uiState by mutableStateOf(DetailUiState())
        private set

    fun DetailKandang(ID: String) {
        viewModelScope.launch {
            uiState = DetailUiState(isLoading = true)
            try {
                val kandang = kandangRepository.getKandangById(ID)
                uiState = DetailUiState(detailUiEvent = kandang!!.toDetailUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = DetailUiState(
                    isError = true,
                    errorMessage = "Failed to fetch details: ${e.message}"
                )
            }
        }
    }

    fun deleteKandangByID(id: String) {
        viewModelScope.launch {
            try {
                kandangRepository.deleteKandang(id)
            } catch (e: IOException) {
                HomeUiStateKandang.Error("Kesalahan jaringan: ${e.message}")
            } catch (e: HttpException) {
                HomeUiStateKandang.Error("Kesalahan server: ${e.message}")
            }
        }
    }
}

data class DetailUiState(
    val detailUiEvent: InsertUiEventKandang = InsertUiEventKandang(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertUiEventKandang()
}

fun Kandang.toDetailUiEvent(): InsertUiEventKandang {
    return InsertUiEventKandang(
        id_kandang = id_kandang.toString(),
        id_hewan = id_hewan,
        kapasitas = kapasitas.toString(),
        lokasi = lokasi,
    )
}