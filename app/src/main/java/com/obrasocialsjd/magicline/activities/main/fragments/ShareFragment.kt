package com.obrasocialsjd.magicline.activities.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.activities.main.general.MainActivity
import kotlinx.android.synthetic.main.layout_rrss.*

class ShareFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_rrss, container, false)
    }

    override fun onStart() {
        super.onStart()
        initRRSSListeners()
    }

    private fun initRRSSListeners() {

        val urlFacebook = getString(R.string.url_facebook)
        val urlInstagram = getString(R.string.url_instagram)
        val urlTwitter = getString(R.string.url_twitter)


        fb_button.setOnClickListener {
            if (activity is MainActivity) (activity as MainActivity).callUriIntent(urlFacebook)
        }

        insta_button.setOnClickListener {
            if (activity is MainActivity) (activity as MainActivity).callUriIntent(urlInstagram)
        }

        twitter_button.setOnClickListener {
            if (activity is MainActivity) (activity as MainActivity).callUriIntent(urlTwitter)
        }
    }
}