package com.obrasocialsjd.magicline.data.models.donations

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "donations")
data class DonationsDBModel (
        @PrimaryKey(autoGenerate = true)
        val primaryKey: Int,
        @ColumnInfo(name = "donationsVal")
        val donationsVal: String?,
        @ColumnInfo(name = "donationsBml")
        val donationsBml: String?,
        @ColumnInfo(name = "donationsBcn")
        val donationsBcn: String?,
        @ColumnInfo(name = "donationsMll")
        val donationsMll: String?
)