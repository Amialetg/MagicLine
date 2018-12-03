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
import com.voluntariat.android.magicline.R.drawable.*
import com.voluntariat.android.magicline.models.DetailModel
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.layout_share.view.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.view.*

class DetailFragment : Fragment () {

    private lateinit var detailLayoutView: View
    private lateinit var detailModel: DetailModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailModel = arguments.get("detailFragment") as DetailModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        detailLayoutView = inflater.inflate(R.layout.fragment_detail, container, false)
        initToolbar()
        initWidgets()
        return detailLayoutView
    }

    override fun onResume() {
        super.onResume()
        if (!detailModel.isBlack){
            detailLayoutView.topToolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_ATOP)
            detailLayoutView.topToolbar.setTitleTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            detailLayoutView.topToolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(context, R.color.black), android.graphics.PorterDuff.Mode.SRC_ATOP)
            detailLayoutView.topToolbar.setTitleTextColor(ContextCompat.getColor(context, R.color.black))
        }
        if (detailModel.hasToolbarImg) { detailLayoutView.topToolbarImg.background = ContextCompat.getDrawable(context, detailModel.toolbarImg) }
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        detailLayoutView.detailTitle.text = detailModel.title
        detailLayoutView.detailSubtitle.text = detailModel.subtitle
        detailLayoutView.detailBody.text = detailModel.textBody
        detailLayoutView.topToolbar.setNavigationIcon(ic_black_cross)
        detailLayoutView.topToolbar.title = detailModel.title
        detailLayoutView.topToolbar.setNavigationOnClickListener { activity.onBackPressed() }
    }

    private fun initWidgets() {

        detailLayoutView.viewOnWeb.setOnClickListener {
            openNewTabWindow(detailModel.link, context)
        }
        detailLayoutView.fb_button.setOnClickListener {
            openNewTabWindow(detailModel.link, context)
        }
        detailLayoutView.insta_button.setOnClickListener {
            openNewTabWindow(detailModel.link, context)
        }
        detailLayoutView.twitter_button.setOnClickListener {
            openNewTabWindow(detailModel.link, context)
        }
    }

    fun openNewTabWindow(url: String, context: Context) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://solidaritat.santjoandedeu.org")))
    }

    companion object {
        fun newInstance(detailModel: DetailModel): DetailFragment {
            val myFragment = DetailFragment()
            val args = Bundle()
            args.putSerializable("detailFragment", detailModel)
            myFragment.arguments = args
            return myFragment
        }
    }
}


