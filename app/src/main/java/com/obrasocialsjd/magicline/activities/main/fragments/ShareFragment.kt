package com.obrasocialsjd.magicline.activities.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.activities.main.general.MainActivity
import kotlinx.android.synthetic.main.layout_rrss.*

class ShareFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_rrss, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var container : ViewGroup = view.parent as ViewGroup
        val scrollView: ViewGroup = (container.getChildAt(0) as ViewGroup).getChildAt(0) as ViewGroup

        // TODO find place to insert view


        val frameLayout: View = container.getChildAt(0)

        /*
        var attr = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        scrollView?.layoutParams = attr
        */

        container.removeAllViews()
        container.addView(frameLayout)
        scrollView.addView(view)


        initRRSSListeners()
    }

    private fun initRRSSListeners() {

        val urlFacebook = getString(R.string.url_facebook)
        val urlInstagram = getString(R.string.url_instagram)
        val urlTwitter = getString(R.string.url_twitter)

        fb_button.setOnClickListener {
            if (activity is MainActivity) (activity as MainActivity).callIntent(urlFacebook)
        }

        insta_button.setOnClickListener {
            if (activity is MainActivity) (activity as MainActivity).callIntent(urlInstagram)
        }

        twitter_button.setOnClickListener {
            if (activity is MainActivity) (activity as MainActivity).callIntent(urlTwitter)
        }
    }
}