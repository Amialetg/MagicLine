package com.voluntariat.android.magicline.Utils

import okhttp3.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MagicLineService {

    @GET
    fun getMarkerDonations(@Query("amount") amount: Double): Call

}