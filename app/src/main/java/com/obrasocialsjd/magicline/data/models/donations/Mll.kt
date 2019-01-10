package com.obrasocialsjd.magicline.data.models.donations

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.obrasocialsjd.magicline.utils.MALLORCA

@Entity(tableName = MALLORCA)
data class Mll(

	@ColumnInfo(name = "amount")
	@field:SerializedName("amount")
	val amount: String? = null
)