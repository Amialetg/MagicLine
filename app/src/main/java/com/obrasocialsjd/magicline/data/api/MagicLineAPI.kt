package com.obrasocialsjd.magicline.data.api

import com.obrasocialsjd.magicline.BuildConfig
import com.obrasocialsjd.magicline.data.MagicLineInterceptor
import com.obrasocialsjd.magicline.data.models.login.LoginModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.obrasocialsjd.magicline.data.models.donations.Response as DonationsResponse
import com.obrasocialsjd.magicline.data.models.posts.Response as PostsResponse
import com.obrasocialsjd.magicline.data.models.teams.Response as TeamsResponse

object MagicLineAPI {
    private const val URL = "http://magiclinesjd.org/ws/" // PROD
//    private const val URL = "http://www.andreurm.cat/ws/" // TEST
    var accessToken: String? = null
    set(value) {
        field = value
        magicLineInterceptor.accessToken = value
        retrofit = getRetrofit()
    }

    private val magicLineInterceptor = MagicLineInterceptor(accessToken)

    private var retrofit = getRetrofit()

    interface MagicLineService {

        @GET("users/{user}/repos")
        fun testAPI(@Path("user") user: String): Call<List<Any>>

        @GET("posts.json")
        fun posts(@Query("lang") lang: String): Call<PostsResponse>

        @GET("marker_donations.json")
        fun donations(): Call<DonationsResponse>

        @FormUrlEncoded
        @POST("oAuthLogin")
        fun oAuthLogin(@Field("username", encoded = true) username: String, @Field("password", encoded = true) password: String) : Call<LoginModel>

        @GET("marker_teams.json")
        fun markerTeams() : Call<TeamsResponse>

        /* MAGICLINE API
        @GET("posts.json")
        fun posts(accessToken:String,page:Int,lang:String,limit:Int) : Call<Any>

        @GET("teams.json")
        fun teams(accessToken:String, page:Int, limit:Int) : Call<Any>

        @GET("marker_donations.json")
        fun marker_donations(accessToken:String) : Call<Any>

        @GET("donations.json")
        fun donations(accessToken:String, page:Int, limit:Int) : Call<Any>
         */
    }

    val service: MagicLineService = retrofit.create(MagicLineService::class.java)

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
                .baseUrl(URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(magicLineInterceptor)
        // add logging as last interceptor
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(loggingInterceptor)
        }
        return clientBuilder.build()
    }
}