package com.voluntariat.android.magicline.data.apimodels

import com.google.gson.annotations.SerializedName

data class PostList(

	@field:SerializedName("posts")
	val posts: List<PostsItem?>? = null
)