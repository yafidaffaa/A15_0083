package com.yafidaffaaa.uas_pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yafidaffaaa.uas_pam.repository.HewanRepository
import com.yafidaffaaa.uas_pam.ui.navigation.DestinasiUpdate
import com.yafidaffaaa.uas_pam.ui.viewmodel.hewan.InsertUiEvent
import com.yafidaffaaa.uas_pam.ui.viewmodel.hewan.InsertUiState
import com.yafidaffaaa.uas_pam.ui.viewmodel.hewan.toHwn
import com.yafidaffaaa.uas_pam.ui.viewmodel.hewan.toUiStateHwn
import kotlinx.coroutines.launch

class UpdateViewModelHewan (
    savedStateHandle: SavedStateHandle,
    private val hwn: HewanRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertUiState())
        private set

    private val _nim: String = checkNotNull(savedStateHandle[DestinasiUpdate.ID])

    init {
        viewModelScope.launch {
            updateUiState = hwn.getHewanById(_nim)!!
                .toUiStateHwn()
        }
    }

    fun updateInsertMhsState(insertUiEvent: InsertUiEvent){
        updateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateMhs(){
        viewModelScope.launch {
            try {
                hwn.updateHewan(_nim, updateUiState.insertUiEvent.toHwn())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}