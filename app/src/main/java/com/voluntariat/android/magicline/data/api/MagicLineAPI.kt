package com.voluntariat.android.magicline.data.api

import com.voluntariat.android.magicline.BuildConfig
import com.voluntariat.android.magicline.data.MagicLineInterceptor
import com.voluntariat.android.magicline.data.apimodels.LoginModel
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

object MagicLineAPI {
    private const val URL = "http://www.andreurm.cat/ws/" //TEST
    private var accessToken: String? = null

    interface MagicLineService {

        @GET("users/{user}/repos")
        fun testAPI(@Path("user") user: String): Call<List<Any>>

        @GET("posts.json")
        fun posts(@Query("access_token") accessToken: String): Call<ResponseBody>

        @FormUrlEncoded
        @POST("oAuthLogin")
        //fun oAuthLogin(@Field("username") userData: String, @Field("password") pwdData: String) : Call<LoginModel>
        //fun oAuthLogin(@Body userData: RequestBody) : Call<LoginModel>
        fun oAuthLogin(@Field("username", encoded = false) username: String, @Field("password", encoded = false) password: String) : Call<LoginModel>
        //fun oAuthLogin(@Body body: RequestBody) : Call<LoginModel>
        //fun oAuthLogin(@Query("username") username: String, @Query("password") password: String) : Call<LoginModel>
        //fun oAuthLogin(@Body userData: HashMap<String, String>) : Call<LoginModel>

        /* MAGICLINE API
        @GET("posts.json")
        fun posts(accessToken:String,page:Int,lang:String,limit:Int) : Call<Any>

        @GET("teams.json")
        fun teams(accessToken:String, page:Int, limit:Int) : Call<Any>

        @GET("marker_teams.json")
        fun marker_teams(accessToken:String) : Call<Any>

        @GET("marker_donations.json")
        fun marker_donations(accessToken:String) : Call<Any>

        @GET("donations.json")
        fun donations(accessToken:String, page:Int, limit:Int) : Call<Any>
         */
    }

    private val retrofit =
            Retrofit.Builder()
                    .baseUrl(URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    val service: MagicLineService = retrofit.create(MagicLineService::class.java)

    private fun getOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(MagicLineInterceptor(accessToken))
        // add logging as last interceptor
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(loggingInterceptor)
        }
        return clientBuilder.build()
    }
}