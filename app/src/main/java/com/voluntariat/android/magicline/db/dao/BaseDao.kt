package com.voluntariat.android.magicline.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*

interface BaseDao<T> {

    @Insert
    fun insert(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(obj: List<T?>?)

    @Update
    fun update(obj: T)

    @Delete
    fun delete(obj: T)
}