package com.voluntariat.android.magicline.data.models.donations

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "donations")
data class Donations(

	@PrimaryKey(autoGenerate = true)
	val primaryKey: Int,

	@Embedded
	@field:SerializedName("bml")
	val bml: Bml? = null,

	@Embedded
	@field:SerializedName("val")
	val valencia: Val? = null,

	@Embedded
	@field:SerializedName("bcn")
	val barcelona: Bcn? = null,

	@Embedded
	@field:SerializedName("mll")
	val mallorca: Mll? = null
)