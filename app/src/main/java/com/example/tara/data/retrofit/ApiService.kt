package com.example.tara.data.retrofit

import com.example.tara.data.response.*
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("/tourist-attractions/{city}")
    suspend fun getTouristAttractionList(
        @Header("Authorization") token: String,
        @Path("city") city: String,
        @Field("userId") userId: String,
    ): TouristAttractionListResponse

    @FormUrlEncoded
    @POST("/users/user-preferences")
    suspend fun setUserPreferences(
        @Header("Authorization") token: String,
        @Field("userId") userId: String,
        @Field("userPreferences") userPreferences: Array<String>
    ): SetUserPreferencesResponse
}