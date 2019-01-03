package com.obrasocialsjd.magicline.data.models.teams

import com.google.gson.annotations.SerializedName

data class JsonMember5(

	@field:SerializedName("companies")
	val companies: Int? = null,

	@field:SerializedName("modality_text")
	val modalityText: String? = null,

	@field:SerializedName("particulars")
	val particulars: Int? = null
)