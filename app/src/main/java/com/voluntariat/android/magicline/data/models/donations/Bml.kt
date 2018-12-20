package com.voluntariat.android.magicline.data.models.donations

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Bml")
data class Bml(

	@ColumnInfo(name = "amount")
	@field:SerializedName("amount")
	val amount: Any? = null
)