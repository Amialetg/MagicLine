package com.voluntariat.android.magicline.data

import androidx.lifecycle.LiveData
import com.voluntariat.android.magicline.data.models.posts.PostsItem

interface MagicLineRepository {
    fun authenticate(
            username: String,
            password: String,
            onResult: (loginResult: Result<String>) -> Unit
    )

    fun getPosts() : LiveData<List<PostsItem>>
}
