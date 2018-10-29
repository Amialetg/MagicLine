package com.voluntariat.android.magicline.Utils

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MagicLineService {

    @GET("users/{user}/repos")
    fun testAPI(@Path("user") user: String): Call<List<Any>>

}