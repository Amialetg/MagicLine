package com.voluntariat.android.magicline.data.apimodels

import com.google.gson.annotations.SerializedName

data class PostsItem(

	@field:SerializedName("PostImage")
	val postImage: List<PostImageItem?>? = null,

	@field:SerializedName("Post")
	val post: Post? = null
)