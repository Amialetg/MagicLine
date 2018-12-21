package com.voluntariat.android.magicline.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.voluntariat.android.magicline.data.models.donations.DonationsDBModel

@Dao interface DonationsDAO : BaseDao<DonationsDBModel> {

    @Query("select * from donations")
    fun getDonations() : LiveData<DonationsDBModel>

}