package com.yafidaffaaa.uas_pam.service

import com.yafidaffaaa.uas_pam.model.KandangWithHewan
import com.yafidaffaaa.uas_pam.model.Monitoring
import com.yafidaffaaa.uas_pam.model.Petugas
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MonitoringService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("/api/monitoring/")
    suspend fun getMonitoring(): List<Monitoring>

    @GET("/api/monitoring/{id}")
    suspend fun getMonitoringById(@Path("id") id: String): Monitoring

    @POST("/api/monitoring/add")
    suspend fun insertMonitoring(@Body monitoring: Monitoring): Monitoring

    @PUT("/api/monitoring/{id}")
    suspend fun editMonitoring(@Path("id") id: String, @Body monitoring: Monitoring): Monitoring

    @DELETE("/api/monitoring/{id}")
    suspend fun deleteMonitoringById(@Path("id") id: String): Response<Void>

    @GET("/api/monitoring/dropdown/hewan")
    suspend fun getNamaHewanByMonitoring(): List<KandangWithHewan>

    @GET("/api/monitoring/dropdown/petugas")
    suspend fun getDropdownPetugas(): List<Petugas>
}