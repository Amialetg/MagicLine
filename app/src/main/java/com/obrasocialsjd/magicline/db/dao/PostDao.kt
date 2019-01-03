package com.obrasocialsjd.magicline.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.obrasocialsjd.magicline.data.models.posts.Post
import com.obrasocialsjd.magicline.data.models.posts.PostsItem

@Dao interface PostDao : BaseDao<PostsItem> {

    @Query("select * from postItems")
    fun getAllPosts() : LiveData<List<PostsItem>>

    @Query("select * from postItems where id = :idPost")
    fun getPostById(idPost: Int): Post

    @Query("DELETE FROM postItems")
    fun nukeTable()

}