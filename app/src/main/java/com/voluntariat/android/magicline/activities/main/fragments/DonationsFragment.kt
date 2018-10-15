package com.voluntariat.android.magicline.activities.main.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.Utils.MagicLineInterceptor
import com.voluntariat.android.magicline.Utils.MagicLineService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DonationsFragment:Fragment(){

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_donations, container,  false)
    }

    fun testApi() {
        val client = OkHttpClient().newBuilder()
                .addInterceptor(MagicLineInterceptor("acces_token"))
                .build()


        val retrofit = Retrofit.Builder()
                .baseUrl("http://www.andreurm.cat/ws")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val magicLineService = retrofit.create(MagicLineService::class.java)
        val amount = 0.0
        val donations = magicLineService.getMarkerDonations(amount)
        val result = donations.execute().body()
        Log.e("TEST","testing API :  " + result + " || " + amount)
    }

}