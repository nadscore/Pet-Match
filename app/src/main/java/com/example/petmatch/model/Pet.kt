package com.example.petmatch.model

data class Pet(
    val id: Long,
    val nome: String,
    val idade: Int,
    val descricao: String,
    val image_uri: String
)