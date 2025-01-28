package com.yafidaffaaa.uas_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hewan(
    @SerialName("Id_hewan") val id_hewan: Int? = null,
    @SerialName("Nama_hewan") val nama_hewan: String,
    @SerialName("Tipe_pakan") val tipe_pakan: String,
    @SerialName("populasi") val populasi: Int?,
    @SerialName("Zona_wilayah") val zona_wilayah: String
)