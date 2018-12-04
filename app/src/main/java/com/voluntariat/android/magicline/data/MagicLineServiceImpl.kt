package com.voluntariat.android.magicline.data

import com.voluntariat.android.magicline.data.api.MagicLineService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MagicLineServiceImpl : MagicLineRepository {

    private val client = OkHttpClient().newBuilder()
            .addInterceptor(MagicLineInterceptor("acces_token"))
            .build()

    private val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val service = retrofit.create(MagicLineService::class.java)

    companion object {
        const val URL = "http://www.andreurm.cat/ws/" //TEST
    }
}