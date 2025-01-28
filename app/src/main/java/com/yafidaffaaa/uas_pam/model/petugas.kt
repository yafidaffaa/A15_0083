package com.yafidaffaaa.uas_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Petugas(
    @SerialName("Id_petugas") val id_petugas: Int? = null,
    @SerialName("Nama_petugas") val nama_petugas: String,
    @SerialName("Jabatan") val jabatan: String,
)