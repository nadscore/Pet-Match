package com.example.petmatch.data.api

import com.example.petmatch.data.entity.PetEntity
import com.example.petmatch.data.entity.UserEntity
import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface PetMatchApiService {
    @GET("rest/v1/Users")
    suspend fun login(
        @Query("email") email: String,
        @Query("senha") senha: String,
        @Query("select") select: String = "*"
    ): Response<List<UserEntity>>

    @GET("rest/v1/Pets")
    suspend fun getPets(
        @Query("select") select: String = "*"
    ): Response<List<PetEntity>>

    @POST("rest/v1/Users")
    @Headers("Prefer: return=minimal")
    suspend fun register(
        @Body userData: Map<String, String>
    ): Response<Unit>

    @POST("rest/v1/Pets")
    @Headers("Prefer: return=minimal")
    suspend fun insertPet(
        @Body petData: JsonObject
    ): Response<Unit>
}