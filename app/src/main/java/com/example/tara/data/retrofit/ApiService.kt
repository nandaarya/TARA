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

    @GET("/tourist-attractions/{city}")
    suspend fun getTouristAttractionList(
        @Header("Authorization") token: String,
        @Path("city") city: String
    ): TouristAttractionListResponse
}