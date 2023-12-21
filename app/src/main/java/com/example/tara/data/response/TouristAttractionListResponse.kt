package com.example.tara.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class TouristAttractionListResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("touristAttractionList")
    val touristAttractionList: List<ListTouristAttractionItem>
) : Parcelable

@Parcelize
data class ListTouristAttractionItem(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("locationName")
	val locationName: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@field:SerializedName("rating")
	val rating: Float,

    @field:SerializedName("userRatingsTotal")
    val userRatingsTotal: Int,

    @field:SerializedName("lat")
    val lat: Double,

    @field:SerializedName("lon")
    val lon: Double,

) : Parcelable
