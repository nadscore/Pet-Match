package com.example.petmatch.model

data class Pet(
    val id: Long,
    val nome: String,
    val idade: Int,
    val descricao: String,
    val imageUrl: String
)

// fake objects
val pet1 = Pet(
    id = 1,
    nome = "Thor",
    idade = 6,
    descricao = "Fofinho",
    imageUrl = "asasda"
)