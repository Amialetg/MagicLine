package com.voluntariat.android.magicline.data.models.donations

import com.google.gson.annotations.SerializedName

data class Donations(

	@field:SerializedName("bml")
	val bml: Bml? = null,

	@field:SerializedName("val")
	val valencia: Val? = null,

	@field:SerializedName("bcn")
	val barcelona: Bcn? = null,

	@field:SerializedName("mll")
	val mallorca: Mll? = null
)