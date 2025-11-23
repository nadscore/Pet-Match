package com.example.petmatch.data.repository

import com.example.petmatch.data.api.RetrofitClient
import com.example.petmatch.model.Pet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PetRepositoryImpl : PetRepository {
    private val api = RetrofitClient.service

    override suspend fun getPets(): Result<List<Pet>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getPets()

            if (response.isSuccessful) {
                val petEntities = response.body() ?: emptyList()

                val pets = petEntities.map { entity ->
                    Pet(
                        id = entity.id,
                        nome = entity.nome,
                        idade = entity.idade,
                        descricao = entity.descricao,
                        imageUrl = entity.imageUrl
                    )
                }

                Result.success(pets)
            } else {
                Result.failure(Exception("Erro ao buscar pets: ${response.code()}"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("Erro de conexão: ${e.message}"))
        }
    }
}