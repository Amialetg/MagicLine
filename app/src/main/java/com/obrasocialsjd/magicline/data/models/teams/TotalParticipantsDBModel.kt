package com.obrasocialsjd.magicline.data.models.teams

import androidx.room.ColumnInfo

data class TotalParticipantsDBModel (

        @ColumnInfo(name = "total")
        val totalParticipants: Int,

        @ColumnInfo(name = "spots")
        val spots: Int


)