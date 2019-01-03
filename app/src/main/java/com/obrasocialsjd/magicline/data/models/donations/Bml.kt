package com.obrasocialsjd.magicline.data.models.donations

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class Bml(

	@ColumnInfo(name = "amount")
	@field:SerializedName("amount")
	val amount: String? = null
)