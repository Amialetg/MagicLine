package com.voluntariat.android.magicline.data

import com.voluntariat.android.magicline.data.apimodels.PostList

interface MagicLineRepository {
    fun authenticate(
            username: String,
            password: String,
            onResult: (loginResult: Result<String>) -> Unit
    )

    fun getPosts(onResult: (loginResult: Result<PostList>) -> Unit)
}
