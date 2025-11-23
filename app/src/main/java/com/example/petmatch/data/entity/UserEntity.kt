package com.example.petmatch.data.entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    @SerialName("id")
    val id: Long,

    @SerialName("nome")
    val nome: String,

    @SerialName("email")
    val email: String,

    @SerialName("senha")
    val senha: String,

    @SerialName("cpf")
    val cpf: String
)
