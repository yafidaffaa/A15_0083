package com.yafidaffaaa.uas_pam.service
import com.yafidaffaaa.uas_pam.model.Hewan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HewanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("/api/hewan/")
    suspend fun getHewan(): List<Hewan>

    @GET("/api/hewan/{id}")
    suspend fun getHewanById(@Path("id") id: String): Hewan

    @POST("/api/hewan/add")
    suspend fun insertHewan(@Body hewan: Hewan): Hewan

    @PUT("/api/hewan/{id}")
    suspend fun editHewan(@Path("id") id: String, @Body hewan: Hewan): Hewan

    @DELETE("/api/hewan/{id}")
    suspend fun deleteHewanByID(@Path("id") id: String): Response<Void>

    @GET("/api/hewan/search/{name}")
    suspend fun searchHewan(@Path("name") name: String): List<Hewan>
}