package com.yafidaffaaa.uas_pam.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Kandang(
    @SerialName("Id_kandang") val id_kandang: Int? = null,
    @SerialName("Id_hewan") val id_hewan: Int? = null,
    @SerialName("kapasitas") val kapasitas: Int,
    @SerialName("lokasi") val lokasi: String
)