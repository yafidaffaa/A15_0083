package com.yafidaffaaa.uas_pam.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Monitoring(
    @SerialName("Id_monitoring") val id_monitoring: Int? = null,
    @SerialName("Id_petugas") val id_petugas: Int? = null,
    @SerialName("Id_kandang") val id_kandang: Int? = null,
    @SerialName("Tanggal_monitoring") val tanggal_monitoring: String? = null,
    @SerialName("Hewan_sakit") val hewan_sakit: Int = 0,
    @SerialName("Hewan_sehat") val hewan_sehat: Int = 0,
    @SerialName("Status") val status: String? = null
)

@Serializable
data class KandangWithHewan(
    val Id_kandang: Int,
    val Nama_hewan: String
)