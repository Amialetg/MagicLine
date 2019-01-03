package com.obrasocialsjd.magicline.data.models.teams

import com.google.gson.annotations.SerializedName

data class Mll(

	@field:SerializedName("1")
	val jsonMember1: JsonMember1? = null,

	@field:SerializedName("2")
	val jsonMember2: JsonMember2? = null,

	@field:SerializedName("3")
	val jsonMember3: JsonMember3? = null
)