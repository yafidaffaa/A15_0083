package com.yafidaffaaa.uas_pam.ui.viewmodel.kandang

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yafidaffaaa.uas_pam.model.Hewan
import com.yafidaffaaa.uas_pam.model.Kandang
import com.yafidaffaaa.uas_pam.repository.HewanRepository
import com.yafidaffaaa.uas_pam.repository.KandangRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InsertViewModelKandang(
    private val kandangRepository: KandangRepository,
    private val hewanRepository: HewanRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertUiStateKandang())
        private set

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

    fun updateInsertKandangState(insertUiEvent: InsertUiEventKandang) {
        uiState = InsertUiStateKandang(insertUiEvent = insertUiEvent)
    }

    suspend fun insertKandang() {
        if (uiState.insertUiEvent.id_hewan == null) {
            Log.e("InsertViewModelKandang", "ID Hewan tidak boleh null!")
            return
        }
        viewModelScope.launch {
            try {
                kandangRepository.insertKandang(uiState.insertUiEvent.toKandang())
                Log.d("InsertViewModelKandang", "Data successfully inserted: ${uiState.insertUiEvent}")
            } catch (e: Exception) {
                Log.e("InsertViewModelKandang", "Error inserting data: ${e.message}", e)
            }
        }
    }

}

data class InsertUiStateKandang(
    val insertUiEvent: InsertUiEventKandang = InsertUiEventKandang()
)

data class InsertUiEventKandang(
    val id_kandang: String = "",
    val id_hewan: Int? = null,
    val kapasitas: String = "",
    val lokasi: String = "",
)

fun InsertUiEventKandang.toKandang(): Kandang = Kandang(
    id_kandang = id_kandang.toIntOrNull(),
    id_hewan = id_hewan,
    kapasitas = kapasitas.toIntOrNull() ?: 0,
    lokasi = lokasi
)

fun Kandang.toUiStateKandang(): InsertUiStateKandang = InsertUiStateKandang(
    insertUiEvent = this.toInsertUiEventKandang()
)

fun Kandang.toInsertUiEventKandang(): InsertUiEventKandang = InsertUiEventKandang(
    id_kandang = id_kandang?.toString() ?: "",
    kapasitas = kapasitas.toString(),
    lokasi = lokasi,
    id_hewan = id_hewan,
)