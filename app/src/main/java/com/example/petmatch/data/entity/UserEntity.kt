package com.example.petmatch.data.entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    @SerialName("id")
    val id: String,

    @SerialName("nome")
    val nome: String,

    @SerialName("cpf")
    val cpf: String
)
