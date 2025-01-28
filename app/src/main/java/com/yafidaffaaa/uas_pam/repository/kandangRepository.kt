package com.yafidaffaaa.uas_pam.repository

import com.yafidaffaaa.uas_pam.model.Kandang
import com.yafidaffaaa.uas_pam.service.KandangService
import java.io.IOException

interface KandangRepository {
    suspend fun getKandang(): List<Kandang>

    suspend fun insertKandang(kandang: Kandang)

    suspend fun updateKandang(ID: String, kandang: Kandang)

    suspend fun deleteKandang(ID: String)

    suspend fun getKandangById(ID: String): Kandang?

    suspend fun getNamaHewanByKandang(ID: String): String

    suspend fun searchKandang(ID: String): List<Kandang>
}

class NetworkKandangRepository(
    private val kandangApiService: KandangService
) : KandangRepository {

    override suspend fun getKandang(): List<Kandang> = kandangApiService.getKandang()

    override suspend fun insertKandang(kandang: Kandang) {
        kandangApiService.insertKandang(kandang)
    }

    override suspend fun updateKandang(ID: String, kandang: Kandang) {
        kandangApiService.editKandang(ID, kandang)
    }

    override suspend fun deleteKandang(ID: String) {
        try {
            val response = kandangApiService.deleteKandangById(ID)
            if (!response.isSuccessful) {
                throw IOException("Gagal menghapus kandang. HTTP status: ${response.code()}")
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getKandangById(ID: String): Kandang {
        return kandangApiService.getKandangById(ID)
    }

    override suspend fun getNamaHewanByKandang(ID: String): String {
        return kandangApiService.getNamaHewanByKandang(ID)
    }

    override suspend fun searchKandang(ID: String): List<Kandang> {
        val idInt = ID.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID format")
        return kandangApiService.searchKandangById(idInt)
    }
}