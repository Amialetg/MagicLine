package com.obrasocialsjd.magicline.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

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