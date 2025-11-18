package com.example.petmatch.data.entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PetEntity(
    @SerialName("id")
    val id: Long,

    @SerialName("nome")
    val nome: String,

    @SerialName("idade")
    val idade: Int,

    @SerialName("descricao")
    val descricao: String,

    @SerialName("image_url")
    val image_url: String
)
