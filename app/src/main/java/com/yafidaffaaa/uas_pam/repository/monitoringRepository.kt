package com.yafidaffaaa.uas_pam.repository

import com.yafidaffaaa.uas_pam.model.KandangWithHewan
import com.yafidaffaaa.uas_pam.model.Monitoring
import com.yafidaffaaa.uas_pam.model.Petugas
import com.yafidaffaaa.uas_pam.service.MonitoringService
import java.io.IOException

interface MonitoringRepository {
    suspend fun getMonitoring(): List<Monitoring>

    suspend fun insertMonitoring(monitoring: Monitoring)

    suspend fun updateMonitoring(ID: String, monitoring: Monitoring)

    suspend fun deleteMonitoring(ID: String)

    suspend fun getMonitoringById(ID: String): Monitoring?

    suspend fun getNamaHewanByMonitoring(): List<KandangWithHewan>

    suspend fun getNamaPetugasDokter(): List<Petugas>
}

class NetworkMonitoringRepository(
    private val monitoringApiService: MonitoringService
) : MonitoringRepository {

    override suspend fun getMonitoring(): List<Monitoring> = monitoringApiService.getMonitoring()

    override suspend fun insertMonitoring(monitoring: Monitoring) {
        monitoringApiService.insertMonitoring(monitoring)
    }

    override suspend fun updateMonitoring(ID: String, monitoring: Monitoring) {
        monitoringApiService.editMonitoring(ID, monitoring)
    }

    override suspend fun deleteMonitoring(ID: String) {
        try {
            val response = monitoringApiService.deleteMonitoringById(ID)
            if (!response.isSuccessful) {
                throw IOException("Gagal menghapus monitoring. HTTP status: ${response.code()}")
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMonitoringById(ID: String): Monitoring {
        return monitoringApiService.getMonitoringById(ID)
    }

    override suspend fun getNamaHewanByMonitoring(): List<KandangWithHewan> {
        return try {
            val result = monitoringApiService.getNamaHewanByMonitoring()
            println("Data Petugas: $result")
            result
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getNamaPetugasDokter(): List<Petugas> {
        return try {
            val result = monitoringApiService.getDropdownPetugas()
            println("Data Petugas: $result")
            result
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}