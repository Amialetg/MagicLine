package com.voluntariat.android.magicline.data

import com.voluntariat.android.magicline.utils.callback
import com.voluntariat.android.magicline.data.api.MagicLineAPI
import com.voluntariat.android.magicline.data.apimodels.PostList


class MagicLineRepositoryImpl(private val service: MagicLineAPI.MagicLineService)
    : MagicLineRepository {

    override fun authenticate(
            username: String,
            password: String,
            onResult: (loginResult: Result<String>) -> Unit) {

        MagicLineAPI.accessToken = "34c5f8837ff1511fa56e7fb23fcb367f4877cd44"
        /**
         * Code that will be used when Login works
         */
        /*MagicLineAPI.accessToken = null
        service.oAuthLogin(username, password).enqueue(callback(
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
        ))*/
    }

    override fun getPosts(
            onResult: (loginResult: Result<PostList>) -> Unit) {
        service.posts().enqueue(callback(
                { result ->
                    if (result.isSuccessful) {
                        onResult(Result.Success(result.body()))
                    } else {
                        onResult(Result.Failure(Throwable(result.errorBody().toString())))
                    }
                }, { error ->
            onResult(Result.Failure(error))
        }))
    }


}