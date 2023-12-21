package com.example.tara.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.tara.data.datastore.UserPreference
import com.example.tara.data.response.ListTouristAttractionItem
import com.example.tara.data.response.LoginResponse
import com.example.tara.data.response.RegisterResponse
import com.example.tara.data.response.SetUserPreferencesResponse
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
                val name = response.loginResult.name
                val userId = response.loginResult.userId
                saveSession(UserModel(userId, name, email, token))
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    fun getTouristAttractionList(userId: String, token: String, city: String): LiveData<Result<List<ListTouristAttractionItem>>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                Log.d("list repository", "Mulai get data list")
                val response = apiService.getTouristAttractionList( "Bearer $token", city, userId)
                Log.d("list repository", response.message)
                val touristAttractionList = response.touristAttractionList
                emit(Result.Success(touristAttractionList))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    fun setUserPreferences(token: String, userId: String, userPreferences: Array<String>): LiveData<Result<SetUserPreferencesResponse>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.setUserPreferences("Bearer $token", userId, userPreferences)
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