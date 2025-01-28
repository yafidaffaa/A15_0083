package com.yafidaffaaa.uas_pam.ui.viewmodel.petugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yafidaffaaa.uas_pam.repository.PetugasRepository
import com.yafidaffaaa.uas_pam.ui.navigation.DestinasiUpdate
import kotlinx.coroutines.launch

class UpdateViewModelPetugas (
    savedStateHandle: SavedStateHandle,
    private val ptg: PetugasRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertUiState())
        private set

    private val _nim: String = checkNotNull(savedStateHandle[DestinasiUpdate.ID])

    init {
        viewModelScope.launch {
            updateUiState = ptg.getPetugasById(_nim)!!
                .toUiStatePtg()
        }
    }

    fun updateInsertPtgState(insertUiEvent: InsertUiEvent){
        updateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updatePtg(){
        viewModelScope.launch {
            try {
                ptg.updatePetugas(_nim, updateUiState.insertUiEvent.toPtg())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}