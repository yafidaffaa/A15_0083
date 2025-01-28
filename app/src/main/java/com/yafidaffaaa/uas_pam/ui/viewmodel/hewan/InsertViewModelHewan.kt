package com.yafidaffaaa.uas_pam.ui.viewmodel.hewan

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yafidaffaaa.uas_pam.model.Hewan
import com.yafidaffaaa.uas_pam.repository.HewanRepository
import kotlinx.coroutines.launch



fun InsertUiEvent.toHwn(): Hewan = Hewan(
    id_hewan = id_hewan.toIntOrNull()?: 0,
    nama_hewan = nama_hewan,
    populasi = populasi.toIntOrNull()?: 0,
    tipe_pakan = tipe_pakan,
    zona_wilayah = zona_wilayah,
)

fun Hewan.toUiStateHwn():InsertUiState = InsertUiState(
    insertUiEvent = this.toInsertUiEvent()
)

fun Hewan.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_hewan = id_hewan.toString(),
    nama_hewan = nama_hewan,
    populasi = populasi.toString(),
    tipe_pakan = tipe_pakan,
    zona_wilayah = zona_wilayah,
)

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id_hewan: String = "",
    val nama_hewan: String = "",
    val populasi: String = "",
    val tipe_pakan: String = "",
    val zona_wilayah: String = "",
)