package com.voluntariat.android.magicline.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

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