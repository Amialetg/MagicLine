package com.voluntariat.android.magicline.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.voluntariat.android.magicline.data.apimodels.Post
import com.voluntariat.android.magicline.data.apimodels.PostsItem

@Dao interface PostDao : BaseDao<PostsItem> {

    @Query("select * from posts")
    fun getAllPosts() : LiveData<List<PostsItem>>

    @Query("select * from posts where id = :idPost")
    fun getPostById(idPost: Int): Post

}