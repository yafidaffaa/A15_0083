package com.yafidaffaaa.uas_pam.dependenciesinjection

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.yafidaffaaa.uas_pam.repository.HewanRepository
import com.yafidaffaaa.uas_pam.repository.KandangRepository
import com.yafidaffaaa.uas_pam.repository.MonitoringRepository
import com.yafidaffaaa.uas_pam.repository.NetworkHewanRepository
import com.yafidaffaaa.uas_pam.repository.NetworkKandangRepository
import com.yafidaffaaa.uas_pam.repository.NetworkMonitoringRepository
import com.yafidaffaaa.uas_pam.repository.NetworkPetugasRepository
import com.yafidaffaaa.uas_pam.repository.PetugasRepository
import com.yafidaffaaa.uas_pam.service.HewanService
import com.yafidaffaaa.uas_pam.service.KandangService
import com.yafidaffaaa.uas_pam.service.MonitoringService
import com.yafidaffaaa.uas_pam.service.PetugasService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val hewanRepository: HewanRepository
    val petugasRepository: PetugasRepository
    val kandangRepository: KandangRepository
    val monitoringRepository: MonitoringRepository
}

class Container : AppContainer {
    private val baseUrl = "http://10.0.2.2:3000/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val hewanService: HewanService by lazy {
        retrofit.create(HewanService::class.java)
    }

    override val hewanRepository: HewanRepository by lazy {
        NetworkHewanRepository(hewanService)
    }

    private val petugasService: PetugasService by lazy {
        retrofit.create(PetugasService::class.java)
    }

    override val petugasRepository: PetugasRepository by lazy {
        NetworkPetugasRepository(petugasService)
    }

    private val kandangService: KandangService by lazy {
        retrofit.create(KandangService::class.java)
    }

    override val kandangRepository: KandangRepository by lazy {
        NetworkKandangRepository(kandangService)
    }

    private val monitoringService: MonitoringService by lazy {
        retrofit.create(MonitoringService::class.java)
    }

    override val monitoringRepository: MonitoringRepository by lazy {
        NetworkMonitoringRepository(monitoringService)
    }
}