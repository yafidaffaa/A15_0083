package com.yafidaffaaa.uas_pam.service

import com.yafidaffaaa.uas_pam.model.Petugas
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PetugasService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("/api/petugas/")
    suspend fun getPetugas(): List<Petugas>

    @GET("/api/petugas/{id}")
    suspend fun getPetugasById(@Path("id") id: String): Petugas

    @POST("/api/petugas/add")
    suspend fun insertPetugas(@Body petugas: Petugas): Petugas

    @PUT("/api/petugas/{id}")
    suspend fun editPetugas(@Path("id") id: String, @Body petugas: Petugas): Petugas

    @DELETE("/api/petugas/{id}")
    suspend fun deletePetugasByID(@Path("id") id: String): Response<Void>

    @GET("/api/petugas/search/{name}")
    suspend fun searchPetugas(@Path("name") name: String): List<Petugas>
}