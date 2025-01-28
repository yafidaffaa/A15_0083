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

