package com.obrasocialsjd.magicline.data.models.donations

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.obrasocialsjd.magicline.utils.BARCELONA

@Entity(tableName = BARCELONA)
data class Bcn(

	@ColumnInfo(name = "total")
	@field:SerializedName("total")
	val total: String? = null
)