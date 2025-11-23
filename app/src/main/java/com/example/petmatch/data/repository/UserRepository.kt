package com.example.petmatch.data.repository

import com.example.petmatch.model.User

interface UserRepository {
    suspend fun login(email: String, senha: String): Result<User>
}