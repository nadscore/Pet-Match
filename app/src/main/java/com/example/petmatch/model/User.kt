package com.example.petmatch.model

data class User(
    val id: String,
    val nome: String,
    val cpf: String,
    val email: String
)

// fake objects
val user1 = User(
    id = "1",
    nome = "Nadine Cabral",
    cpf = "50595600824",
    email = "nadine.cabral336@gmail.com"
)