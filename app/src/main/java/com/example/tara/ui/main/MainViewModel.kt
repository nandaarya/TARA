package com.example.tara.ui.main

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tara.data.Repository
import com.example.tara.data.Result
import com.example.tara.data.UserModel
import com.example.tara.data.response.RegisterResponse
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

//    private fun getToken(): String {
//        var token = ""
//        viewModelScope.launch {
//            repository.getSession().collect{ user ->
//                token = user.token
//            }
//        }
//        return token
//    }

    private val _userLocation = MediatorLiveData<Location>()
    val userLocation: LiveData<Location> = _userLocation

    fun saveUserLocation(userLocation: Location) {
        _userLocation.value = userLocation
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}