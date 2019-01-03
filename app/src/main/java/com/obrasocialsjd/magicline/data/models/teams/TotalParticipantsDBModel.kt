package com.obrasocialsjd.magicline.data.models.teams

import androidx.room.ColumnInfo

data class TotalParticipantsDBModel (

        @ColumnInfo(name = "totalParticulars")
        val totalParticulars: Int,

        @ColumnInfo(name = "totalCompanies")
        val totalCompanies: Int,

        @ColumnInfo(name = "total")
        val totalParticipants: Int
)