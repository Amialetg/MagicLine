package com.obrasocialsjd.magicline.activities.main.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.R.drawable.ic_black_cross
import com.obrasocialsjd.magicline.activities.main.otherui.CirclePagerIndicatorDecorationForDetailPage
import com.obrasocialsjd.magicline.models.DetailModel
import com.obrasocialsjd.magicline.utils.htmlToSpanned
import com.obrasocialsjd.magicline.activities.main.adapters.SlideViewAdapter
import com.obrasocialsjd.magicline.activities.main.adapters.SlideViewNewsImgAdapter
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.layout_share.view.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.view.*
import kotlin.collections.ArrayList

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
        return detailLayoutView
    }

    override fun onResume() {
        super.onResume()
        initImagesRecycler()

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
        detailLayoutView.topToolbar.setNavigationIcon(ic_black_cross)

        if (detailModel.titleToolbar.isEmpty()) {
            detailLayoutView.topToolbar.title = detailModel.title
        } else {
            detailLayoutView.topToolbar.title = detailModel.titleToolbar
        }
        detailLayoutView.topToolbar.setNavigationOnClickListener { this.requireActivity().onBackPressed()

        }
    }

    private fun initContent() {
        detailLayoutView.detailTitle.text = detailModel.title
        detailLayoutView.detailSubtitle.text = detailModel.subtitle
        detailLayoutView.detailBody.text = detailModel.textBody.htmlToSpanned()
    }

    private fun initImagesRecycler() {

        val myImagesManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        imagesRecyclerView.layoutManager = myImagesManager
        imagesRecyclerView.setPadding(0,0,0,50)
        //Adding pager behaviour
        val snapHelper = PagerSnapHelper()

        if (detailModel.listToolbarImg.isNotEmpty()) {
            myImagesAdapter = SlideViewAdapter(ArrayList())
            imagesRecyclerView.adapter = myImagesAdapter
            //load data inside the RecyclerView
            myImagesAdapter.loadItems(detailModel.listToolbarImg)

            if (detailModel.listToolbarImg.size > 1) {
                imagesRecyclerView.onFlingListener = null //<-- We add this line to avoid the app crashing when returning from the background
                snapHelper.attachToRecyclerView(imagesRecyclerView)
                imagesRecyclerView.addItemDecoration(CirclePagerIndicatorDecorationForDetailPage())
            }
        } else if (detailModel.listPostImg.isNotEmpty()) {
            myNewsImgAdapter = SlideViewNewsImgAdapter(ArrayList())
            imagesRecyclerView.adapter = myNewsImgAdapter
            myNewsImgAdapter.loadItems(detailModel.listPostImg)

            if (detailModel.listPostImg.size > 1) {
                imagesRecyclerView.onFlingListener = null //<-- We add this line to avoid the app crashing when returning from the background
                snapHelper.attachToRecyclerView(imagesRecyclerView)
                imagesRecyclerView.addItemDecoration(CirclePagerIndicatorDecorationForDetailPage())
            }
        }
    }

    private fun initWidgets() {

        detailLayoutView.viewOnWeb.setOnClickListener {
            openNewTabWindow(detailModel.link, this.requireContext())
        }
        detailLayoutView.fb_button.setOnClickListener {
            openNewTabWindow(detailModel.link, this.requireContext())
        }
        detailLayoutView.insta_button.setOnClickListener {
            openNewTabWindow(detailModel.link, this.requireContext())
        }
        detailLayoutView.twitter_button.setOnClickListener {
            openNewTabWindow(detailModel.link, this.requireContext())
        }
    }

    private fun openNewTabWindow(url: String, context: Context) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
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


