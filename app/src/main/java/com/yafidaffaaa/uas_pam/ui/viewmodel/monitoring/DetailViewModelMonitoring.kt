package com.yafidaffaaa.uas_pam.ui.viewmodel.monitoring

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yafidaffaaa.uas_pam.model.Monitoring
import com.yafidaffaaa.uas_pam.repository.MonitoringRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class DetailViewModelMonitoring(private val monitoringRepository: MonitoringRepository) : ViewModel() {

    var uiState by mutableStateOf(DetailUiStateMonitoring())
        private set

    fun getDetailMonitoring(ID: String) {
        viewModelScope.launch {
            uiState = DetailUiStateMonitoring(isLoading = true)
            try {
                val monitoring = monitoringRepository.getMonitoringById(ID)
                uiState = DetailUiStateMonitoring(detailUiEvent = monitoring!!.toDetailUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = DetailUiStateMonitoring(
                    isError = true,
                    errorMessage = "Gagal mengambil data monitoring: ${e.message}"
                )
            }
        }
    }

    fun deleteMonitoringByID(id: String) {
        viewModelScope.launch {
            try {
                monitoringRepository.deleteMonitoring(id)
            } catch (e: IOException) {
                uiState = uiState.copy(isError = true, errorMessage = "Kesalahan jaringan: ${e.message}")
            } catch (e: HttpException) {
                uiState = uiState.copy(isError = true, errorMessage = "Kesalahan server: ${e.message}")
            }
        }
    }
}

data class DetailUiStateMonitoring(
    val detailUiEvent: InsertUiEventMonitoring = InsertUiEventMonitoring(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertUiEventMonitoring()
}

fun Monitoring.toDetailUiEvent(): InsertUiEventMonitoring {
    return InsertUiEventMonitoring(
        id_monitoring = id_monitoring,
        id_petugas = id_petugas,
        id_kandang = id_kandang,
        tanggal_monitoring = tanggal_monitoring.toString(),
        hewan_sakit = hewan_sakit,
        hewan_sehat = hewan_sehat,
        status = status.toString()
    )
}