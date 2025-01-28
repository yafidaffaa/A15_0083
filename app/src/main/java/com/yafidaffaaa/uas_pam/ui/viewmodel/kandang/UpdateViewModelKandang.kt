package com.yafidaffaaa.uas_pam.ui.viewmodel.kandang

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yafidaffaaa.uas_pam.model.Hewan
import com.yafidaffaaa.uas_pam.repository.HewanRepository
import com.yafidaffaaa.uas_pam.repository.KandangRepository
import com.yafidaffaaa.uas_pam.ui.navigation.DestinasiUpdate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpdateViewModelKandang(
    savedStateHandle: SavedStateHandle,
    private val kandangRepo: KandangRepository,
    private val hewanRepository: HewanRepository
) : ViewModel() {
    var updateUiState by mutableStateOf(InsertUiStateKandang())
        private set

    private val _idKandang: String = checkNotNull(savedStateHandle[DestinasiUpdate.ID])
    private val _hewanList = MutableStateFlow<List<Hewan>>(emptyList())
    val hewanList: StateFlow<List<Hewan>> = _hewanList.asStateFlow()

    init {
        fetchHewanList()
    }

    private fun fetchHewanList() {
        viewModelScope.launch {
            try {
                val response = hewanRepository.getHewan()
                _hewanList.value = response
            } catch (e: Exception) {
                Log.e("InsertViewModelKandang", "Error fetching hewan list: ${e.message}", e)
            }
        }
    }

    init {
        viewModelScope.launch {
            updateUiState = kandangRepo.getKandangById(_idKandang)?.toUiStateKandang()
                ?: InsertUiStateKandang()
        }
    }

    fun updateInsertKandangState(insertUiEvent: InsertUiEventKandang) {
        updateUiState = InsertUiStateKandang(insertUiEvent = insertUiEvent)
    }

    suspend fun updateKandang() {
        viewModelScope.launch {
            try {
                kandangRepo.updateKandang(_idKandang, updateUiState.insertUiEvent.toKandang())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}