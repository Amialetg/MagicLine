package com.obrasocialsjd.magicline.activities.main.fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.activities.main.general.MainActivity
import com.obrasocialsjd.magicline.models.DetailModel
import com.obrasocialsjd.magicline.utils.*
import kotlinx.android.synthetic.main.fragment_about_us.view.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*

class AboutUsFragment: BaseFragment() {
    private lateinit var detailLayoutView: View
    private lateinit var detailModel: DetailModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailModel = arguments?.get(Fragment.Detail.toString()) as DetailModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        detailLayoutView = inflater.inflate(R.layout.fragment_about_us, container, false)
        initToolbar()
        initContent()
        initWidgets()
        initRrss()
        return detailLayoutView
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        detailLayoutView.crossBtnAboutUs.setBackgroundResource(R.drawable.ic_white_cross)
        detailLayoutView.crossBtnAboutUs.setOnClickListener { this.requireActivity().onBackPressed() }
    }

    private fun initWidgets() {
        detailLayoutView.viewOnWebAboutUs.setOnClickListener {
            (activity as MainActivity).openUrl(detailModel.link)
        }
    }

    private fun initContent() {
        detailLayoutView.detailTitleAboutUs.text = detailModel.title
        detailLayoutView.detailSubtitleAboutUs.text = detailModel.subtitle
        detailLayoutView.detailBodyAboutUs.text = detailModel.textBody.htmlToSpanned()
        detailLayoutView.detailBodyAboutUs.movementMethod = LinkMovementMethod.getInstance()

    }

    private fun initRrss() {
        detailLayoutView.rrssViewDetailAboutUs.fbListener = { activity?.openShareActivity(shareApp(getString(R.string.fb_pkg))) }
        detailLayoutView.rrssViewDetailAboutUs.instaListener = { activity?.openShareActivity(shareApp(getString(R.string.insta_pkg))) }
        detailLayoutView.rrssViewDetailAboutUs.twitterListener = { activity?.openShareActivity(shareApp(getString(R.string.twitter_pkg))) }
    }

    companion object {
        fun newInstance(dataModel: DetailModel): BaseFragment {
            val myFragment = AboutUsFragment()
            val args = Bundle()
            args.putSerializable(Fragment.Detail.toString(), dataModel)
            myFragment.arguments = args
            return myFragment
        }
    }
}