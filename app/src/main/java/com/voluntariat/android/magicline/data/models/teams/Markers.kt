package com.voluntariat.android.magicline.data.models.teams

import com.google.gson.annotations.SerializedName

data class Markers(

	@field:SerializedName("val")
	val valencia: Val? = null,

	@field:SerializedName("bcn")
	val barcelona: Bcn? = null,

	@field:SerializedName("mll")
	val mallorca: Mll? = null
)