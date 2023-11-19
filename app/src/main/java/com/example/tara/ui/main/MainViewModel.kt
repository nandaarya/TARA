package com.example.tara.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tara.data.Repository
import com.example.tara.data.UserModel
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

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}