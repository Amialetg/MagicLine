package com.voluntariat.android.magicline.data.models.teams

import com.google.gson.annotations.SerializedName

data class JsonMember3(

	@field:SerializedName("companies")
	val companies: Int? = null,

	@field:SerializedName("modality_text")
	val modalityText: String? = null,

	@field:SerializedName("particulars")
	val particulars: Int? = null
)