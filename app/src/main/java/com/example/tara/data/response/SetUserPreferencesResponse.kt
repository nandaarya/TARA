package com.example.tara.data.response

import com.google.gson.annotations.SerializedName

data class SetUserPreferencesResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
