package com.voluntariat.android.magicline.data.apimodels

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "postImages")
data class PostImageItem(

	@ColumnInfo(name = "img")
	@field:SerializedName("img")
	val img: String? = null,

	@ColumnInfo(name = "post_id")
	@field:SerializedName("post_id")
	val postId: String? = null,

	@ColumnInfo(name = "weight")
	@field:SerializedName("weight")
	val weight: String? = null,

	@ColumnInfo(name = "id")
	@field:SerializedName("id")
	val id: String? = null
)