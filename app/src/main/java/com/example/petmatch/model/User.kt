package com.example.petmatch.model

data class User(
    val id: Long,
    val nome: String,
    val email: String,
    val senha: String,
    val cpf: String
)
