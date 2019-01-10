package com.obrasocialsjd.magicline.data.models.posts

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "postItems")
data class PostsItem(

        @PrimaryKey(autoGenerate = true)
        val primaryKey: Int,
        @Embedded
        @SerializedName("Post")
        val post : Post,
        @SerializedName("PostImage")
        val postImages : List<PostImageItem>
)