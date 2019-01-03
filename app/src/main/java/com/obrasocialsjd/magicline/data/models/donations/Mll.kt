package com.obrasocialsjd.magicline.data.models.donations

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Mll")
data class Mll(

	@ColumnInfo(name = "amount")
	@field:SerializedName("amount")
	val amount: String? = null
)