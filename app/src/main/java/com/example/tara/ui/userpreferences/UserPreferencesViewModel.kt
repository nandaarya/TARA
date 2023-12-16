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

    fun setUserPreferences(userPreferences: List<String>) {
        val liveData = repository.setUserPreferences(getUserId(), userPreferences)
        Log.d("user preferences", "userId: ${getUserId()}, userPreferences: $userPreferences")
        _userPreferencesResponse.addSource(liveData) { result ->
            _userPreferencesResponse.value = result
        }
    }
}