package com.voluntariat.android.magicline.data.models.donations

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Val")
data class Val(

	@ColumnInfo(name = "amount")
	@field:SerializedName("amount")
	val amount: String? = null
)