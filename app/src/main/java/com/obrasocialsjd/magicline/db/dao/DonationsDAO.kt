package com.obrasocialsjd.magicline.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.obrasocialsjd.magicline.data.models.donations.DonationsDBModel

@Dao interface DonationsDAO : BaseDao<DonationsDBModel> {

    @Query("select * from donations")
    fun getDonations() : LiveData<DonationsDBModel>

    @Query("DELETE FROM donations")
    fun nukeTable()
}