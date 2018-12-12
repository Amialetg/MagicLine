package com.voluntariat.android.magicline.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface BaseDao<T> {

    @Insert
    fun insert(obj: T)

    @Insert
    fun insertList(obj: List<T?>?)

    @Update
    fun update(obj: T)

    @Delete
    fun delete(obj: T)
}