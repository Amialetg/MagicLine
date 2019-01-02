package com.obrasocialsjd.magicline.data.apimodels

import com.google.gson.annotations.SerializedName

data class Post(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("created")
	val created: String? = null,

	@field:SerializedName("weight")
	val weight: String? = null,

	@field:SerializedName("modified")
	val modified: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("locale")
	val locale: String? = null,

	@field:SerializedName("scheduleCardTitle")
	val title: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("teaser")
	val teaser: String? = null
)