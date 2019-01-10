package com.obrasocialsjd.magicline.data.models.teams

import com.google.gson.annotations.SerializedName

data class JsonMember3(

	@field:SerializedName("companies")
	val companies: Int? = null,

	@field:SerializedName("modality_text")
	val modalityText: String? = null,

	@field:SerializedName("particulars")
	val particulars: Int? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("percentage")
	val percentage: Double? = null

)