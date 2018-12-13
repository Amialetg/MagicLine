package com.voluntariat.android.magicline.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.voluntariat.android.magicline.data.MagicLineRepository
import com.voluntariat.android.magicline.data.MagicLineRepositoryImpl
import com.voluntariat.android.magicline.data.apimodels.Post
import com.voluntariat.android.magicline.data.apimodels.PostsItem
import com.voluntariat.android.magicline.db.MagicLineDB


class MagicLineViewModel(application : Application, private val repository: MagicLineRepository) : AndroidViewModel(application) {

    private lateinit var mAllPosts: LiveData<List<Post>>

    fun getAllPosts(): LiveData<List<Post>> {
        return mAllPosts
    }

    fun insert(postsItem: PostsItem) {
        // TODO("implement!")
        //repository.insert(postsItem)
    }


}