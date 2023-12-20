package com.example.tara.ui.userpreferences

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tara.data.Repository
import com.example.tara.data.Result
import com.example.tara.data.response.SetUserPreferencesResponse
import kotlinx.coroutines.launch

class UserPreferencesViewModel(private val repository: Repository): ViewModel() {

    private val _userPreferencesResponse = MediatorLiveData<Result<SetUserPreferencesResponse>>()
    val userPreferencesResponse: LiveData<Result<SetUserPreferencesResponse>> = _userPreferencesResponse

    private fun getUserId(): String {
        var userId = ""
        viewModelScope.launch {
            repository.getSession().collect{ user ->
                userId = user.userId
            }
        }
        return userId
    }

    private fun getToken(): String {
        var token = ""
        viewModelScope.launch {
            repository.getSession().collect{ user ->
                token = user.token
            }
        }
        return token
    }

    fun setUserPreferences(userPreferences: Array<String>) {
        val liveData = repository.setUserPreferences(getToken(), getUserId(), userPreferences)
        _userPreferencesResponse.addSource(liveData) { result ->
            _userPreferencesResponse.value = result
        }
    }
}