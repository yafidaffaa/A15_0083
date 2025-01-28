package com.yafidaffaaa.uas_pam.ui.viewmodel.monitoring

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yafidaffaaa.uas_pam.model.KandangWithHewan
import com.yafidaffaaa.uas_pam.model.Petugas
import com.yafidaffaaa.uas_pam.model.Monitoring
import com.yafidaffaaa.uas_pam.repository.KandangRepository
import com.yafidaffaaa.uas_pam.repository.MonitoringRepository
import com.yafidaffaaa.uas_pam.repository.PetugasRepository
import com.yafidaffaaa.uas_pam.ui.navigation.DestinasiUpdateMonitoring
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class UpdateViewModelMonitoring(
    savedStateHandle: SavedStateHandle,
    private val monitoringRepository: MonitoringRepository
) : ViewModel() {

    var updateUiState by mutableStateOf(InsertUiStateMonitoring())
        private set

    private val _idMonitoring: String = savedStateHandle[DestinasiUpdateMonitoring.ID] ?: ""
    private val _monitoringList = MutableStateFlow<List<Monitoring>>(emptyList())
//    val monitoringList: StateFlow<List<Monitoring>> = _monitoringList.asStateFlow()

    private val _petugasList = MutableStateFlow<List<Petugas>>(emptyList())
    val petugasList: StateFlow<List<Petugas>> = _petugasList.asStateFlow()

    private val _kandangList = MutableStateFlow<List<KandangWithHewan>>(emptyList())
    val kandangList: StateFlow<List<KandangWithHewan>> = _kandangList.asStateFlow()

    init {
        fetchPetugasList()
        fetchKandangList()
        fetchMonitoringList()
        loadMonitoringData()
    }

    private fun fetchMonitoringList() {
        viewModelScope.launch {
            try {
                val response = monitoringRepository.getMonitoring()
                _monitoringList.value = response
            } catch (e: Exception) {
                Log.e("UpdateViewModelMonitoring", "Error fetching monitoring list: ${e.message}", e)
            }
        }
    }

    private fun loadMonitoringData() {
        if (_idMonitoring.isBlank()) {
            Log.e("UpdateViewModelMonitoring", "No monitoring ID provided")
            return
        }
        viewModelScope.launch {
            val monitoring = monitoringRepository.getMonitoringById(_idMonitoring)
            updateUiState = monitoring?.toUiStateMonitoring() ?: InsertUiStateMonitoring()

            fetchPetugasList()
            fetchKandangList()
        }
    }

    private fun fetchPetugasList() {
        viewModelScope.launch {
            try {
                val response = monitoringRepository.getNamaPetugasDokter()
                _petugasList.value = response
            } catch (e: Exception) {
                Log.e("UpdateViewModelMonitoring", "Error fetching petugas list: ${e.message}", e)
            }
        }
    }

    private fun fetchKandangList() {
        viewModelScope.launch {
            try {
                val response = monitoringRepository.getNamaHewanByMonitoring()
                _kandangList.value = response
            } catch (e: Exception) {
                Log.e("UpdateViewModelMonitoring", "Error fetching kandang list: ${e.message}", e)
            }
        }
    }

    fun updateInsertMonitoringState(insertUiEvent: InsertUiEventMonitoring) {
        updateUiState = InsertUiStateMonitoring(insertUiEvent = insertUiEvent)
    }

    suspend fun updateMonitoring() {
        viewModelScope.launch {
            try {
                val monitoringData = updateUiState.insertUiEvent.toMonitoring()
                Log.d("UpdateViewModelMonitoring", "Data yang dikirim ke server: ${Json.encodeToString(monitoringData)}")

                Log.d("UpdateViewModelMonitoring", "Sending data: $monitoringData")

                monitoringRepository.updateMonitoring(_idMonitoring, monitoringData)

                Log.d("UpdateViewModelMonitoring", "Data berhasil diperbarui: $monitoringData")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("UpdateViewModelMonitoring", "Gagal memperbarui data: HTTP ${e.code()} - $errorBody", e)
            } catch (e: Exception) {
                Log.e("UpdateViewModelMonitoring", "Exception: ${e.message}", e)
            }
        }
    }
}