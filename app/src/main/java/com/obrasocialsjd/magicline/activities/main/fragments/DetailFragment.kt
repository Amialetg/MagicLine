package com.obrasocialsjd.magicline.activities.main.fragments

import android.app.ActionBar
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
import com.voluntariat.android.magicline.activities.main.adapters.SlideViewAdapter
import kotlinx.android.synthetic.main.fragment_detail.*
import com.obrasocialsjd.magicline.activities.main.otherui.CirclePagerIndicatorDecorationForDetailPage
import com.obrasocialsjd.magicline.models.DetailModel
import com.obrasocialsjd.magicline.utils.htmlToSpanned
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.layout_share.view.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.view.*
import java.util.ArrayList

class DetailFragment : BaseFragment() {

    private lateinit var detailLayoutView: View
    private lateinit var detailModel: DetailModel
    private lateinit var myImagesAdapter: SlideViewAdapter
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

        if (!detailModel.hasToolbarImg){
            initImagesRecycler()
        }

        if (!detailModel.isBlack){
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
            var layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.bottomMargin = 50
            detailLayoutView.topToolbar.setBackgroundResource(detailModel.toolbarImg[0])
            detailLayoutView.appbar.layoutParams = layoutParams
        }
        detailLayoutView.topToolbar.setNavigationIcon(ic_black_cross)

        if(detailModel.titleToolbar.isNullOrEmpty()){
            detailLayoutView.topToolbar.title = detailModel.title
        }else{
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
        val myImagesManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        myImagesAdapter = SlideViewAdapter(ArrayList())

        imagesRecyclerView.layoutManager = myImagesManager
        imagesRecyclerView.adapter = myImagesAdapter
        imagesRecyclerView.setPadding(0,0,0,50)

        if(detailModel.toolbarImg.size != 1){

            //Adding pager behaviour
            val snapHelper = PagerSnapHelper()

            imagesRecyclerView.onFlingListener = null //<-- We add this line to avoid the app crashing when returning from the background
            snapHelper.attachToRecyclerView(imagesRecyclerView)
            imagesRecyclerView.addItemDecoration(CirclePagerIndicatorDecorationForDetailPage())
        }

        //load data inside the RecyclerView
        myImagesAdapter.loadItems(detailModel.toolbarImg)

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


