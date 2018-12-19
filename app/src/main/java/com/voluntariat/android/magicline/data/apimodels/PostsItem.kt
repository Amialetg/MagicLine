package com.voluntariat.android.magicline.data.apimodels

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "postItems")
data class PostsItem(

        @PrimaryKey(autoGenerate = true)
        val primaryKey: Int = 0,
        @Embedded
        val post : Post,
        val postImages : MutableList<PostImageItem>
)