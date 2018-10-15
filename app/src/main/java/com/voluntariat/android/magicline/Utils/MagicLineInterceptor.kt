package com.voluntariat.android.magicline.Utils

import okhttp3.Interceptor
import okhttp3.Response


class MagicLineInterceptor(val access_token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url().newBuilder()
                .addQueryParameter("acces_token", access_token)
                .addQueryParameter("format", "json")
                .build()

        val newRequest = request.newBuilder()
                .url(url)
                .build()

        return chain.proceed(newRequest)
    }
}