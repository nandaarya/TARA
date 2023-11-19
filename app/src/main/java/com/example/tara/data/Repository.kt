package com.example.tara.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.tara.data.datastore.UserPreference
import com.example.tara.data.response.LoginResponse
import com.example.tara.data.response.RegisterResponse
import com.example.tara.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
) {

    private suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.register(name, email, password)
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.login(email, password)
                val token = response.loginResult.token
                saveSession(UserModel(email, token))
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference,
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, userPreference)
            }.also { instance = it }
    }
}