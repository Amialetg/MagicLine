package com.voluntariat.android.magicline.data

import okhttp3.Interceptor
import okhttp3.Response

class MagicLineInterceptor(var accessToken: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (accessToken.isNullOrEmpty()) return chain.proceed(originalRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build())

        val url = originalRequest.url().newBuilder()
                .addQueryParameter("access_token", accessToken)
                .build()
        return chain.proceed(originalRequest.newBuilder()
                .url(url)
                .header("Content-Type", "application/json")
                .build())
    }
}