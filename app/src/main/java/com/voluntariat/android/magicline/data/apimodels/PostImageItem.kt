package com.voluntariat.android.magicline.data.apimodels

import com.google.gson.annotations.SerializedName

data class PostImageItem(

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("post_id")
	val postId: String? = null,

	@field:SerializedName("weight")
	val weight: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)