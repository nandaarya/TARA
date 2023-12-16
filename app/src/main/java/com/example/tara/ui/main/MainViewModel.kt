package com.example.tara.ui.main

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tara.data.Repository
import com.example.tara.data.Result
import com.example.tara.data.UserModel
import com.example.tara.data.response.ListTouristAttractionItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private fun getToken(): String {
        var token = ""
        viewModelScope.launch {
            repository.getSession().collect{ user ->
                token = user.token
            }
        }
        return token
    }

    private fun getUserId(): String {
        var userId = ""
        viewModelScope.launch {
            repository.getSession().collect{ user ->
                userId = user.userId
            }
        }
        return userId
    }

    private val _userLocation = MediatorLiveData<Location>()
    val userLocation: LiveData<Location> = _userLocation

    private val _touristAttractionList = MediatorLiveData<Result<List<ListTouristAttractionItem>>>()
    val touristAttractionList: LiveData<Result<List<ListTouristAttractionItem>>> = _touristAttractionList

    fun getTouristAttractionList(city: String) {
        val liveData = repository.getTouristAttractionList(getToken(), city, getUserId())
        Log.d("list", "city: $city")
        _touristAttractionList.addSource(liveData) { result ->
            _touristAttractionList.value = result
        }
    }

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