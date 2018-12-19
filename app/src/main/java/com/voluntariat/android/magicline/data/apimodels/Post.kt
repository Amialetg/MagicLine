package com.voluntariat.android.magicline.data.apimodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts")
data class Post(
        @ColumnInfo(name = "id")
        @field:SerializedName("id")
        val id: String? = null,

        @ColumnInfo(name = "date")
        @field:SerializedName("date")
        val date: String? = null,

        @ColumnInfo(name = "img" )
        @field:SerializedName("img")
        val img: String? = null,

        @ColumnInfo(name = "created" )
        @field:SerializedName("created")
        val created: String? = null,

        @ColumnInfo(name = "weight")
        @field:SerializedName("weight")
        val weight: String? = null,

        @ColumnInfo(name = "modified")
        @field:SerializedName("modified")
        val modified: String? = null,

        @ColumnInfo(name = "text")
        @field:SerializedName("text")
        val text: String? = null,

        @ColumnInfo(name = "locale")
        @field:SerializedName("locale")
        val locale: String? = null,

        @ColumnInfo(name = "title")
        @field:SerializedName("title")
        val title: String? = null,

        @ColumnInfo(name = "slug")
        @field:SerializedName("slug")
        val slug: String? = null,

        @ColumnInfo(name = "url")
        @field:SerializedName("url")
        val url: String? = null,

        @ColumnInfo(name = "teaser")
        @field:SerializedName("teaser")
        val teaser: String? = null
)