package com.voluntariat.android.magicline.data

import android.app.Application
import androidx.lifecycle.LiveData
import android.os.AsyncTask
import com.voluntariat.android.magicline.utils.callback
import com.voluntariat.android.magicline.data.api.MagicLineAPI
import com.voluntariat.android.magicline.data.apimodels.PostList
import com.voluntariat.android.magicline.data.apimodels.PostsItem
import com.voluntariat.android.magicline.db.MagicLineDB
import com.voluntariat.android.magicline.db.dao.PostDao


class MagicLineRepositoryImpl(database: MagicLineDB?)
    : MagicLineRepository {

    private lateinit var mPostDao: PostDao
    private val mAllPosts: LiveData<List<PostsItem>>

    init {
        val db = database
        if (db != null) {
            mPostDao = db.postDao()
        }
        mAllPosts = mPostDao.getAllPosts()
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: PostDao) : AsyncTask<PostsItem, Void, Void>() {

        override fun doInBackground(vararg params: PostsItem): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    fun insert(postItem: PostsItem) {
        InsertAsyncTask(mPostDao).execute(postItem)
    }


    override fun authenticate(
            username: String,
            password: String,
            onResult: (loginResult: Result<String>) -> Unit) {

        MagicLineAPI.accessToken = null
        MagicLineAPI.service.oAuthLogin(username, password).enqueue(callback(
            { result ->
                if (result.isSuccessful) {
                    result.body()?.loginModelClient?.accessToken?.let { accessToken ->
                        MagicLineAPI.accessToken = accessToken
                        onResult(Result.Success(accessToken))
                    } ?: onResult(Result.Failure(Throwable("Unexpected response")))
                } else {
                    onResult(Result.Failure(Throwable(result.errorBody().toString())))
                }
            }, { error ->
                onResult(Result.Failure(error))
            }
        ))
    }

    override fun getPosts(
            onResult: (postResult: Result<PostList>) -> Unit) {
        MagicLineAPI.service.posts().enqueue(callback(
                { result ->
                    if (result.isSuccessful) {
                        mPostDao.insertList(result.body()?.posts)
                    } else {
                        onResult(Result.Failure(Throwable(result.errorBody().toString())))
                    }
                }, { error ->
            onResult(Result.Failure(error))
        }))
    }


}