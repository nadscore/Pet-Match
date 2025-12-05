package com.example.petmatch.data.repository

import com.example.petmatch.model.User

interface UserRepository {
    suspend fun login(email: String, senha: String): Result<User>
    suspend fun register(nome: String, cpf: String, email: String, senha: String): Result<Unit>
}