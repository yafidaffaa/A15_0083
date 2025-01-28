package com.yafidaffaaa.uas_pam.repository

import com.yafidaffaaa.uas_pam.model.Petugas
import com.yafidaffaaa.uas_pam.service.PetugasService
import java.io.IOException


interface PetugasRepository {
    suspend fun getPetugas(): List<Petugas>

    suspend fun insertPetugas(petugas: Petugas)

    suspend fun updatePetugas(ID: String, petugas: Petugas)

    suspend fun deletePetugas(ID: String)

    suspend fun getPetugasById(ID: String): Petugas?

    suspend fun searchPetugas(name: String): List<Petugas>

}

class NetworkPetugasRepository(
    private val petugasApiService: PetugasService
): PetugasRepository {

    override suspend fun getPetugas(): List<Petugas> =
        petugasApiService.getPetugas()

    override suspend fun insertPetugas(petugas: Petugas) {
        petugasApiService.insertPetugas(petugas)
    }

    override suspend fun updatePetugas(ID: String, petugas: Petugas){
        petugasApiService.editPetugas(ID,petugas)
    }

    override suspend fun deletePetugas(ID: String) {
        try {
            val response = petugasApiService.deletePetugasByID(ID)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete petugas. HTTP status code: ${response.code()}")
            }else{
                response.message()
                println(response.message())
            }
        }catch (e:Exception) {
            throw e
        }
    }

    override suspend fun getPetugasById(ID: String): Petugas {
        return petugasApiService.getPetugasById(ID)
    }

    override suspend fun searchPetugas(name: String): List<Petugas> {
        return petugasApiService.searchPetugas(name)
    }

}