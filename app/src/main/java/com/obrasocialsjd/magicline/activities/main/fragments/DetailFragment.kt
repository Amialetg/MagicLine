package com.obrasocialsjd.magicline.activities.main.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.activities.main.adapters.SlideViewAdapter
import com.obrasocialsjd.magicline.activities.main.adapters.SlideViewNewsImgAdapter
import com.obrasocialsjd.magicline.activities.main.general.MainActivity
import com.obrasocialsjd.magicline.activities.main.otherui.CirclePagerIndicatorDecorationForDetailPage
import com.obrasocialsjd.magicline.models.DetailModel
import com.obrasocialsjd.magicline.utils.shareApp
import com.obrasocialsjd.magicline.utils.htmlToSpanned
import com.obrasocialsjd.magicline.utils.openShareActivity
import com.obrasocialsjd.magicline.utils.openUrl
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.view.*

class DetailFragment : BaseFragment() {

    private lateinit var detailLayoutView: View
    private lateinit var detailModel: DetailModel
    private lateinit var myImagesAdapter: SlideViewAdapter
    private lateinit var myNewsImgAdapter: SlideViewNewsImgAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailModel = arguments?.get("detailFragment") as DetailModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        detailLayoutView = inflater.inflate(R.layout.fragment_detail, container, false)
        initToolbar()
        initContent()
        initWidgets()
        initRrss()
        return detailLayoutView
    }

    override fun onResume() {
        super.onResume()
        if (!detailModel.hasToolbarImg){ initImagesRecycler()}
        if (!detailModel.isBlack) {
            detailLayoutView.topToolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this.requireContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_ATOP)
            detailLayoutView.topToolbar.setTitleTextColor(ContextCompat.getColor(this.requireContext(), R.color.white))
        } else {
            detailLayoutView.topToolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this.requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_ATOP)
            detailLayoutView.topToolbar.setTitleTextColor(ContextCompat.getColor(this.requireContext(), R.color.black))
        }
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        if (detailModel.hasToolbarImg) {
            val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.bottomMargin = resources.getDimension(R.dimen.aboutAppMargin).toInt()
            detailLayoutView.topToolbar.setBackgroundResource(detailModel.listToolbarImg[0])
            detailLayoutView.appbar.layoutParams = layoutParams
        }
        if (detailModel.titleToolbar.isEmpty()) {
            detailLayoutView.topToolbar.title = detailModel.title
        } else {
            detailLayoutView.topToolbar.title = detailModel.titleToolbar
        }
        detailLayoutView.topToolbar.setNavigationIcon(R.drawable.ic_black_cross)

        detailLayoutView.topToolbar.setNavigationOnClickListener { this.requireActivity().onBackPressed()

        }
    }

    private fun initContent() {
        detailLayoutView.detailTitle.text = detailModel.title
        detailLayoutView.detailSubtitle.text = detailModel.subtitle
        detailLayoutView.detailBody.text = detailModel.textBody.htmlToSpanned()
        detailLayoutView.detailBody.movementMethod = LinkMovementMethod.getInstance()

    }

    private fun initImagesRecycler() {

        val myImagesManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        imagesRecyclerView.layoutManager = myImagesManager
        imagesRecyclerView.setPadding(0,0,0,50)
        //Adding pager behaviour
        val snapHelper = PagerSnapHelper()

        if (detailModel.listToolbarImg.isNotEmpty()) {
            myImagesAdapter = SlideViewAdapter(detailModel.listToolbarImg)
            imagesRecyclerView.adapter = myImagesAdapter

            if (detailModel.listToolbarImg.size > 1) {
                imagesRecyclerView.onFlingListener = null //<-- We add this line to avoid the app crashing when returning from the background
                snapHelper.attachToRecyclerView(imagesRecyclerView)
                imagesRecyclerView.addItemDecoration(CirclePagerIndicatorDecorationForDetailPage())
            }
        } else if (detailModel.listPostImg.isNotEmpty()) {
            myNewsImgAdapter = SlideViewNewsImgAdapter(detailModel.listPostImg)
            imagesRecyclerView.adapter = myNewsImgAdapter

            if (detailModel.listPostImg.size > 1) {
                imagesRecyclerView.onFlingListener = null //<-- We add this line to avoid the app crashing when returning from the background
                snapHelper.attachToRecyclerView(imagesRecyclerView)
                imagesRecyclerView.addItemDecoration(CirclePagerIndicatorDecorationForDetailPage())
            }
        }
    }

    private fun initWidgets() {
        detailLayoutView.viewOnWeb.setOnClickListener {
            (activity as MainActivity).openUrl(detailModel.link)
        }
    }

    private fun initRrss() {
        detailLayoutView.rrssViewDetail.fbListener = { activity?.openShareActivity(shareApp(getString(R.string.fb_pkg))) }
        detailLayoutView.rrssViewDetail.instaListener = { activity?.openShareActivity(shareApp(getString(R.string.insta_pkg))) }
        detailLayoutView.rrssViewDetail.twitterListener = { activity?.openShareActivity(shareApp(getString(R.string.twitter_pkg))) }
    }

    companion object {
        fun newInstance(dataModel: DetailModel): BaseFragment {
            val myFragment = DetailFragment()
            val args = Bundle()
            args.putSerializable("detailFragment", dataModel)
            myFragment.arguments = args
            return myFragment
        }
    }
}


