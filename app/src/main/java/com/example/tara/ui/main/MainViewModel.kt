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

    private var _touristAttractionList = MediatorLiveData<Result<List<ListTouristAttractionItem>>>()
    var touristAttractionList: LiveData<Result<List<ListTouristAttractionItem>>> = _touristAttractionList

    private var _filteredTouristAttractionList = MediatorLiveData<Result<List<ListTouristAttractionItem>>>()

    fun getTouristAttractionList(city: String) {
        val liveData = repository.getTouristAttractionList(getToken(), city, getUserId())
        _touristAttractionList.addSource(liveData) { result ->
            _touristAttractionList.value = result
        }
    }

    fun findTouristAttraction(query: String): List<ListTouristAttractionItem> {
        val touristAttractionListResult = _touristAttractionList.value ?: return emptyList()

        val filteredList = when (touristAttractionListResult) {
            is Result.Success -> {
                val list = touristAttractionListResult.data.filter { attraction ->
                    attraction.locationName.contains(query, ignoreCase = true)
                }
                list
            }
            is Result.Loading -> {
                emptyList()
            }
            is Result.Error -> {
                emptyList()
            }
        }

        return filteredList
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