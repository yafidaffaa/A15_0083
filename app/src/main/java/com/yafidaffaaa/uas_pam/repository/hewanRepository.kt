package com.yafidaffaaa.uas_pam.repository

import com.yafidaffaaa.uas_pam.model.Hewan
import com.yafidaffaaa.uas_pam.service.HewanService
import java.io.IOException


interface HewanRepository {
    suspend fun getHewan(): List<Hewan>

    suspend fun insertHewan(hewan: Hewan)

    suspend fun updateHewan(ID: String, hewan: Hewan)

    suspend fun deleteHewan(ID: String)

    suspend fun getHewanById(ID: String): Hewan?

    suspend fun searchHewan(name: String): List<Hewan>

}

class NetworkHewanRepository(
    private val hewanApiService: HewanService
): HewanRepository {
    override suspend fun getHewan(): List<Hewan> = hewanApiService.getHewan()

    override suspend fun insertHewan(hewan: Hewan) {
        hewanApiService.insertHewan(hewan)
    }

    override suspend fun updateHewan(ID: String, hewan: Hewan) {
        hewanApiService.editHewan(ID,hewan)
    }

    override suspend fun deleteHewan(ID: String) {
        try {
            val response = hewanApiService.deleteHewanByID(ID)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete hewan. HTTP status code: ${response.code()}")
            }else{
                response.message()
                println(response.message())
            }
        }catch (e:Exception) {
            throw e
        }
    }

    override suspend fun getHewanById(ID: String): Hewan {
        return hewanApiService.getHewanById(ID)
    }

    override suspend fun searchHewan(name: String): List<Hewan> {
        return hewanApiService.searchHewan(name)
    }
}