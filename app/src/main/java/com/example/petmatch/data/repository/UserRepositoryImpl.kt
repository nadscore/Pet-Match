package com.example.petmatch.data.repository

import com.example.petmatch.data.api.RetrofitClient
import com.example.petmatch.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl : UserRepository {

    private val api = RetrofitClient.service

    override suspend fun login(email: String, senha: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = api.login(
                email = "eq.$email",
                senha = "eq.$senha"
            )

            if (response.isSuccessful) {
                val usersList = response.body()

                if (!usersList.isNullOrEmpty()) {
                    val entity = usersList[0]

                    val userModel = User(
                        id = entity.id,
                        nome = entity.nome,
                        email = entity.email,
                        senha = entity.senha,
                        cpf = entity.cpf
                    )

                    Result.success(userModel)
                } else {
                    Result.failure(Exception("Email ou senha incorretos"))
                }
            } else {
                Result.failure(Exception("Erro no servidor: ${response.code()}"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("Erro de conexão: ${e.message}"))
        }
    }
}