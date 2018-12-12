package com.voluntariat.android.magicline.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.voluntariat.android.magicline.data.MagicLineRepositoryImpl
import com.voluntariat.android.magicline.data.apimodels.Post
import com.voluntariat.android.magicline.data.apimodels.PostsItem


class MagicLineViewModel(application: Application) : AndroidViewModel(application) {

    private var mRepository: MagicLineRepositoryImpl = MagicLineRepositoryImpl(application)

    private lateinit var mAllPosts: LiveData<List<Post>>

    fun getAllPosts(): LiveData<List<Post>> {
        return mAllPosts
    }

    fun insert(postsItem: PostsItem) {
        mRepository.insert(postsItem)
    }


}