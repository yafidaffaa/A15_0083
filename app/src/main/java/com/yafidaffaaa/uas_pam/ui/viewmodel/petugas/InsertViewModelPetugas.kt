package com.yafidaffaaa.uas_pam.ui.viewmodel.petugas

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yafidaffaaa.uas_pam.model.Petugas
import com.yafidaffaaa.uas_pam.repository.PetugasRepository
import kotlinx.coroutines.launch

class InsertViewModelPetugas(private val ptg: PetugasRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertPtgState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertPtg() {
        viewModelScope.launch {
            try {
                ptg.insertPetugas(uiState.insertUiEvent.toPtg())
                Log.d("InsertViewModel", "Data successfully inserted: ${uiState.insertUiEvent}")
            } catch (e: Exception) {
                Log.e("InsertViewModel", "Error inserting data: ${e.message}", e)
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id_petugas: String = "",
    val nama_petugas: String = "",
    val jabatan: String = "",
)

fun InsertUiEvent.toPtg(): Petugas = Petugas(
    id_petugas = id_petugas.toIntOrNull()?: 0,
    nama_petugas = nama_petugas,
    jabatan = jabatan,
)

fun Petugas.toUiStatePtg():InsertUiState = InsertUiState(
    insertUiEvent = this.toInsertUiEvent()
)

fun Petugas.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_petugas = id_petugas.toString(),
    nama_petugas = nama_petugas,
    jabatan = jabatan,
)