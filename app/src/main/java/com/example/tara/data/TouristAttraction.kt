package com.example.tara.data

data class TouristAttraction(
    val id: Int = 0,
    val locationName: String,
    val description: String,
    val photoUrl: String,
    val rating: Float,
    val userRatingsTotal: Int,
    val lat: Long,
    val lon: Long,
)
