package com.example.petmatch.data.repository

import com.example.petmatch.model.Pet

interface PetRepository {
    suspend fun getPets(): Result<List<Pet>>
    suspend fun savePet(nome: String, idade: Int, descricao: String, image_uri: String): Result<Unit>
}