package com.yafidaffaaa.uas_pam.ui.viewmodel.monitoring

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.yafidaffaaa.uas_pam.model.Monitoring
import com.yafidaffaaa.uas_pam.repository.MonitoringRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeUiStateMonitoring {
    data class Success(val monitoring: List<Monitoring>) : HomeUiStateMonitoring()
    object Loading : HomeUiStateMonitoring()
    data class Error(val message: String) : HomeUiStateMonitoring()
}

class HomeViewModelMonitoring(private val monitoringRepo: MonitoringRepository) : ViewModel() {
    var monitoringUIState: HomeUiStateMonitoring by mutableStateOf(HomeUiStateMonitoring.Loading)
        private set

    init {
        getMonitoring()
    }

    fun getMonitoring() {
        viewModelScope.launch {
            monitoringUIState = HomeUiStateMonitoring.Loading
            monitoringUIState = try {
                HomeUiStateMonitoring.Success(monitoringRepo.getMonitoring())
            } catch (e: IOException) {
                Log.e("MonitoringViewModel", "Kesalahan jaringan: ${e.message}", e)
                HomeUiStateMonitoring.Error("Kesalahan jaringan: ${e.message}")
            } catch (e: HttpException) {
                Log.e("MonitoringViewModel", "Kesalahan server: ${e.message}", e)
                HomeUiStateMonitoring.Error("Kesalahan server: ${e.message}")
            } catch (e: Exception) {
                Log.e("MonitoringViewModel", "Terjadi kesalahan tak terduga: ${e.message}", e)
                HomeUiStateMonitoring.Error("Terjadi kesalahan tak terduga: ${e.message}")
            }
        }
    }
}