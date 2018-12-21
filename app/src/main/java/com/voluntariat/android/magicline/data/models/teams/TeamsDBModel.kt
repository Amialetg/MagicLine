package com.voluntariat.android.magicline.data.models.teams

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teamsMarkers")
data class TeamsDBModel (

        @PrimaryKey(autoGenerate = true)
        val primaryKey: Int,

        @ColumnInfo(name = "city")
        val city: String,

        @ColumnInfo(name = "modality")
        val modality: String?,

        @ColumnInfo(name = "companies")
        val companies: Int?,

        @ColumnInfo(name = "particulars")
        val particulars: Int?
)