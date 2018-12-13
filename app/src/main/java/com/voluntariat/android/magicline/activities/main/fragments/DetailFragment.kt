package com.voluntariat.android.magicline.activities.main.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.R.drawable.ic_black_cross
import com.voluntariat.android.magicline.activities.main.DataModelInterface
import com.voluntariat.android.magicline.models.DetailModel
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.layout_share.view.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.view.*

class DetailFragment : BaseFragment(), DataModelInterface {

    private lateinit var detailLayoutView: View
    private lateinit var detailModel: DetailModel
    private lateinit var slideUpAnimation: Animation
    private lateinit var slideDownAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        slideUpAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_up)
        slideDownAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_up)
        detailModel = arguments?.get("detailFragment") as DetailModel
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
            detailLayoutView.topToolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this.requireContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_ATOP)
            detailLayoutView.topToolbar.setTitleTextColor(ContextCompat.getColor(this.requireContext(), R.color.white))
        } else {
            detailLayoutView.topToolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this.requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_ATOP)
            detailLayoutView.topToolbar.setTitleTextColor(ContextCompat.getColor(this.requireContext(), R.color.black))
        }
        if (detailModel.hasToolbarImg) {
            detailLayoutView.topToolbarImg.background = ContextCompat.getDrawable(this.requireContext(), detailModel.toolbarImg) }
    }
//
    override fun onPause() {
        super.onPause()
        detailLayoutView.startAnimation(slideDownAnimation)
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        detailLayoutView.detailTitle.text = detailModel.title
        detailLayoutView.detailSubtitle.text = detailModel.subtitle
        detailLayoutView.detailBody.text = detailModel.textBody
        detailLayoutView.topToolbar.setNavigationIcon(ic_black_cross)
        detailLayoutView.topToolbar.title = detailModel.title
        detailLayoutView.topToolbar.setNavigationOnClickListener { this.requireActivity().onBackPressed()
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

    fun openNewTabWindow(url: String, context: Context) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://solidaritat.santjoandedeu.org")))
    }

    override fun newInstance(detailModel: DataModelInterface): BaseFragment {
        val myFragment = DetailFragment()
        val args = Bundle()
        args.putSerializable("detailFragment", detailModel)
        myFragment.arguments = args
        return myFragment
    }
}


