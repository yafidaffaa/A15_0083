package com.yafidaffaaa.uas_pam.service

import com.yafidaffaaa.uas_pam.model.Kandang
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KandangService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("/api/kandang/")
    suspend fun getKandang(): List<Kandang>

    @GET("/api/kandang/{id}")
    suspend fun getKandangById(@Path("id") id: String): Kandang

    @POST("/api/kandang/add")
    suspend fun insertKandang(@Body kandang: Kandang): Kandang

    @PUT("/api/kandang/{id}")
    suspend fun editKandang(@Path("id") id: String, @Body kandang: Kandang): Kandang

    @DELETE("/api/kandang/{id}")
    suspend fun deleteKandangById(@Path("id") id: String): Response<Void>

    @GET("/api/kandang/hewan/{id_kandang}")
    suspend fun getNamaHewanByKandang(@Path("id_kandang") id_kandang: String): String

    @GET("/api/kandang/search/{id}")
    suspend fun searchKandangById(@Path("id") id: Int): List<Kandang>

}