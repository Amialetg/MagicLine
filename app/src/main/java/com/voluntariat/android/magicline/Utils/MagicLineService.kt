package com.voluntariat.android.magicline.Utils

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MagicLineService {

    @GET("users/{user}/repos")
    fun testAPI(@Path("user") user: String): Call<List<Any>>

    /* MAGICLINE API
    @POST("oAuthLogin")
    fun oAuthLogin(username:String, password:String) : Call<Any>

    @GET("posts.json")
    fun posts(access_token:String,page:Int,lang:String,limit:Int) : Call<Any>

    @GET("teams.json")
    fun teams(access_token:String, page:Int, limit:Int) : Call<Any>

    @GET("marker_teams.json")
    fun marker_teams(access_token:String) : Call<Any>

    @GET("marker_donations.json")
    fun marker_donations(access_token:String) : Call<Any>

    @GET("donations.json")
    fun donations(access_token:String, page:Int, limit:Int) : Call<Any>
     */


}