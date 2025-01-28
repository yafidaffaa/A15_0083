package com.yafidaffaaa.uas_pam.ui.viewmodel.monitoring

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yafidaffaaa.uas_pam.model.KandangWithHewan
import com.yafidaffaaa.uas_pam.model.Monitoring
import com.yafidaffaaa.uas_pam.model.Petugas
import com.yafidaffaaa.uas_pam.repository.MonitoringRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class InsertViewModelMonitoring(
    private val monitoringRepository: MonitoringRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertUiStateMonitoring())
        private set

    private val _petugasList = MutableStateFlow<List<Petugas>>(emptyList())
    private val _kandangList = MutableStateFlow<List<KandangWithHewan>>(emptyList())

    val petugasList: StateFlow<List<Petugas>> = _petugasList.asStateFlow()
    val kandangList: StateFlow<List<KandangWithHewan>> = _kandangList.asStateFlow()

    init {
        fetchPetugasList()
        fetchKandangList()
    }

    private fun fetchPetugasList() {
        viewModelScope.launch {
            try {
                val response = monitoringRepository.getNamaPetugasDokter()

                Log.d("API Response", Json.encodeToString(response))
                Log.d("API Response", response.toString())
                _petugasList.value = response
            } catch (e: Exception) {
                Log.e("InsertViewModelMonitoring", "Error fetching petugas list: ${e.message}", e)
            }
        }
    }

    private fun fetchKandangList() {
        viewModelScope.launch {
            try {
                val response = monitoringRepository.getNamaHewanByMonitoring()
                _kandangList.value = response
                Log.d("API Response", Json.encodeToString(response))
                Log.d("API Response", response.toString())

            } catch (e: Exception) {
                Log.e("InsertViewModelMonitoring", "Error fetching kandang list: ${e.message}", e)
            }
        }
    }

    fun updateInsertMonitoringState(insertUiEvent: InsertUiEventMonitoring) {
        uiState = InsertUiStateMonitoring(insertUiEvent = insertUiEvent)
    }

    suspend fun insertMonitoring() {
        viewModelScope.launch {
            try {
                val monitoringData = uiState.insertUiEvent.toMonitoring()

                Log.d("InsertViewModelMonitoring", "Data yang dikirim: ${Json.encodeToString(monitoringData)}")

                monitoringRepository.insertMonitoring(monitoringData)
            } catch (e: HttpException) {
                Log.e("InsertViewModelMonitoring", "HTTP error: ${e.response()?.errorBody()?.string()}")
            } catch (e: Exception) {
                Log.e("InsertViewModelMonitoring", "Gagal memasukkan data: ${e.message}", e)
            }
        }
    }
}

data class InsertUiStateMonitoring(
    val insertUiEvent: InsertUiEventMonitoring = InsertUiEventMonitoring()
)

data class InsertUiEventMonitoring(
    val id_monitoring: Int? = null,
    val id_petugas: Int? = null,
    val id_kandang: Int? = null,
    val tanggal_monitoring: String = "",
    val hewan_sakit: Int = 0,
    val hewan_sehat: Int = 0,
    val status: String = ""
)

fun InsertUiEventMonitoring.toMonitoring(): Monitoring {
    return Monitoring(
        id_monitoring = id_monitoring,
        id_petugas = id_petugas,
        id_kandang = id_kandang,
        tanggal_monitoring = tanggal_monitoring,
        hewan_sakit = hewan_sakit,
        hewan_sehat = hewan_sehat,
        status = status
    )
}

fun Monitoring.toUiStateMonitoring(): InsertUiStateMonitoring {
    return InsertUiStateMonitoring(
        insertUiEvent = InsertUiEventMonitoring(
            id_monitoring = this.id_monitoring,
            id_petugas = this.id_petugas,
            id_kandang = this.id_kandang,
            tanggal_monitoring = this.tanggal_monitoring.toString(),
            hewan_sakit = this.hewan_sakit,
            hewan_sehat = this.hewan_sehat,
            status = this.status.toString()
        )
    )
}