package com.obrasocialsjd.magicline.activities.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.obrasocialsjd.magicline.R
import kotlinx.android.synthetic.main.layout_rrss.*

class ShareFragment : BaseFragment(), FragmentActions {

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
            callUriIntent(requireActivity(), urlFacebook)
        }

        insta_button.setOnClickListener {
            callUriIntent(requireActivity(), urlInstagram)
        }

        twitter_button.setOnClickListener {
            callUriIntent(requireActivity(), urlTwitter)
        }
    }
}