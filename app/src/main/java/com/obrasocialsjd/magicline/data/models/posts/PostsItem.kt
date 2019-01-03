package com.obrasocialsjd.magicline.data.models.posts

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "postItems")
data class PostsItem(

        @PrimaryKey(autoGenerate = true)
        val primaryKey: Int,
        @Embedded
        @SerializedName("Post")
        val post : Post,
        @SerializedName("PostImage")
        val postImages : MutableList<PostImageItem>
)