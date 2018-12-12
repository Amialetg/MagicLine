package com.voluntariat.android.magicline.activities.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voluntariat.android.magicline.R

class DonationsFragment: androidx.fragment.app.Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_donations, container,  false)
        testApi()
    }

    private fun testApi() {
        /*val loginModelClient = OkHttpClient().newBuilder()
                .addInterceptor(MagicLineInterceptor("acces_token"))
                .build()




        val magicLineService = retrofit.create(MagicLineService::class.java)


        val call = magicLineService.testAPI("Test")
        val result = call.execute().body()
        Log.e("API",result.toString())*/
    }
}