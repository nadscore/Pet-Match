package com.example.petmatch.model

data class User(
    val id: Long,
    val nome: String,
    val email: String,
    val senha: String,
    val cpf: String
)

// fake objects
val user1 = User(
    id = 1,
    nome = "Nadine Cabral",
    email = "nadine.cabral336@gmail.com",
    senha = "123",
    cpf = "50595600824"
)