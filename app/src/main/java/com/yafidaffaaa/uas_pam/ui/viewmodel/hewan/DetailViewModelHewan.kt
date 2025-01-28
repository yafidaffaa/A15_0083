package com.yafidaffaaa.uas_pam.ui.viewmodel.hewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yafidaffaaa.uas_pam.model.Hewan
import com.yafidaffaaa.uas_pam.repository.HewanRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class DetailViewModelHewan(private val hwnRepository: HewanRepository) : ViewModel() {

    var uiState by mutableStateOf(DetailUiState())
        private set

    fun DetailHewan(ID: String) {
        viewModelScope.launch {
            uiState = DetailUiState(isLoading = true)
            try {
                val hewan =
                    hwnRepository.getHewanById(ID) // ini membuat berat memory karena ambil data dari variable bukan dari route
                uiState = DetailUiState(detailUiEvent = hewan!!.toDetailUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = DetailUiState(
                    isError = true,
                    errorMessage = "Failed to fetch details: ${e.message}"
                )
            }
        }

    }

    fun deleteHewanByID(id: String) {
        viewModelScope.launch {
            try {
                hwnRepository.deleteHewan(id)
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

fun Hewan.toDetailUiEvent(): InsertUiEvent {
    return InsertUiEvent(
        id_hewan = id_hewan.toString(),
        nama_hewan = nama_hewan,
        tipe_pakan = tipe_pakan,
        populasi = populasi.toString(),
        zona_wilayah = zona_wilayah,
    )
}
