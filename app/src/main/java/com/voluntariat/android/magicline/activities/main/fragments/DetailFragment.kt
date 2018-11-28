package com.voluntariat.android.magicline.activities.main.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.R.drawable.about_us
import com.voluntariat.android.magicline.R.drawable.ic_news_left_arrow
import com.voluntariat.android.magicline.R.string.viewOnWeb
import com.voluntariat.android.magicline.models.DetailModel
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.layout_share.view.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.view.*

class DetailFragment : Fragment () {

    private lateinit var detailLayoutView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        detailLayoutView = inflater.inflate(R.layout.fragment_detail, container, false)
        initWidgets()
        initToolbar()
        return detailLayoutView
    }

    private fun initToolbar() {
        val detailModel = DetailModel(title = getString(R.string.share), text = getString(R.string.share), subtitle = getString(R.string.share), link = getString(R.string.share))
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        detailLayoutView.topToolbarImg.background = ContextCompat.getDrawable(context, about_us)
        detailLayoutView.topToolbar.title = getString(viewOnWeb)
        detailLayoutView.topToolbar.setNavigationIcon(ic_news_left_arrow)
    }

    private fun initWidgets() {

        val dataSet = getDataset()

        detailLayoutView.viewOnWeb.setOnClickListener{
            openNewTabWindow("www.google.es", context)
        }
        detailLayoutView.fb_button.setOnClickListener{
            openNewTabWindow("www.facebook.es", context)
        }
        detailLayoutView.insta_button.setOnClickListener{
            openNewTabWindow("www.instagram.es", context)
        }
        detailLayoutView.twitter_button.setOnClickListener{
            openNewTabWindow("www.twitter.es", context)
        }
    }

    fun openNewTabWindow(url: String, context : Context) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun getDataset(): DetailModel{
        return DetailModel(title = "", link = "", subtitle = "", text = "")
    }
}


