package com.example.petmatch.data.entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Resposta do endpoint /auth/v1/token
 */
@Serializable
data class LoginResponseEntity(
    @SerialName("access_token")
    val accessToken: String,

    @SerialName("user")
    val user: AuthUserEntity
)

@Serializable
data class AuthUserEntity(
    @SerialName("id")
    val id: String,

    @SerialName("email")
    val email: String
)