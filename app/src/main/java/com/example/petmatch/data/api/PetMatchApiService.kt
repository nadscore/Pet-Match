package com.example.petmatch.data.api

import com.example.petmatch.data.entity.PetEntity
import com.example.petmatch.data.entity.UserEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PetMatchApiService {
    @GET("rest/v1/users")
    suspend fun login(
        @Query("email") email: String,
        @Query("senha") senha: String,
        @Query("select") select: String = "*"
    ): Response<List<UserEntity>>

    @GET("rest/v1/pets")
    suspend fun getPets(
        @Query("select") select: String = "*"
    ): Response<List<PetEntity>>
}