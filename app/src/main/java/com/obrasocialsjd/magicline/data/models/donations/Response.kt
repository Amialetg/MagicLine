package com.obrasocialsjd.magicline.data.models.donations

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("donations")
	val donations: Donations? = null
)