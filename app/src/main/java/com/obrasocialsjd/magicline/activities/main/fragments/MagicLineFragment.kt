package com.obrasocialsjd.magicline.activities.main.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.activities.main.adapters.NewsAdapter
import com.obrasocialsjd.magicline.activities.main.general.MainActivity
import com.obrasocialsjd.magicline.utils.TrackingUtils
import com.obrasocialsjd.magicline.activities.main.otherui.CirclePagerIndicatorDecoration
import com.obrasocialsjd.magicline.data.MagicLineRepositoryImpl
import com.obrasocialsjd.magicline.data.models.donations.DonationsDBModel
import com.obrasocialsjd.magicline.data.models.posts.PostsItem
import com.obrasocialsjd.magicline.db.MagicLineDB
import com.obrasocialsjd.magicline.models.DetailModel
import com.obrasocialsjd.magicline.models.NewsModel
import com.obrasocialsjd.magicline.utils.*
import com.obrasocialsjd.magicline.viewModel.MagicLineViewModel
import com.obrasocialsjd.magicline.viewModel.MagicLineViewModelFactory
import kotlinx.android.synthetic.main.fragment_magic_line.*
import kotlinx.android.synthetic.main.layout_a_fons.*
import kotlinx.android.synthetic.main.layout_countdown_bottom.*
import kotlinx.android.synthetic.main.layout_countdown_top.*
import kotlinx.android.synthetic.main.layout_mes_que.*
import kotlinx.android.synthetic.main.layout_news.*
import java.util.*

class MagicLineFragment : BaseFragment() {

    private lateinit var mMagicLineViewModel: MagicLineViewModel
    private lateinit var myNewsAdapter: NewsAdapter

    //Programming section widgets
    lateinit var progRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = MagicLineRepositoryImpl(MagicLineDB.getDatabase(requireActivity().applicationContext))
        val factory = MagicLineViewModelFactory(requireActivity().application, repository)
        mMagicLineViewModel = ViewModelProviders.of(this, factory).get(MagicLineViewModel::class.java)
    }
    //Setting the corresponding view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_magic_line, container, false)
    }

    override fun onStart() {
        super.onStart()

        //Init TextViews, etc
        val txtArray = initWidgets()

        initCountDown(txtArray)

        initMoreInfoMLListener()

        initNewsRecycler()

        initMesQueListeners()

        initAfonsListeners()

        initRrss()
    }

    private fun initRrss() {
        rrssView.fbListener = { activity?.openShareActivity(shareApp(getString(R.string.fb_pkg))) }
        rrssView.instaListener = { activity?.openShareActivity(shareApp(getString(R.string.insta_pkg))) }
        rrssView.twitterListener = { activity?.openShareActivity(shareApp(getString(R.string.twitter_pkg))) }
    }

    private fun initMesQueListeners() {

        btnDonateTeam.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(requireContext(), DonationsFragment.newInstance(), analyticsScreen = TrackingUtils.Screens.Donations)
        }

        btnRequestDonates.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(requireContext(), InviteFriendsFragment.newInstance(), analyticsScreen = TrackingUtils.Screens.InviteFriends)
        }

        btnBrainStorm.setOnClickListener { (activity as MainActivity).openUrl(getString(R.string.pdf_donations_collecting_guide)) }
    }

    private fun initMoreInfoMLListener() {
        moreInfoML.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(context = requireContext(),fragment = MoreInfoMLFragment.newInstance(), analyticsScreen = TrackingUtils.Screens.MagicLineInfo)
        }
    }

    private fun initWidgets(): Array<TextView> {
        return arrayOf(countdownDays, countdown_hores, countdownMin, countdownSec)
    }

    private fun initCountDown(txtDies: Array<TextView>) {
        val currentTime: Long = Calendar.getInstance().timeInMillis
        val dateLong: Long = resources.getString(R.string.magicLineDate).toLong()
        val diff: Long = dateLong - currentTime
        MyCounter(diff, MILLIS, txtDies).start()
    }

    private fun initNewsRecycler() {
        val myNewsManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        myNewsAdapter = NewsAdapter(emptyList())

        newsRecyclerView.layoutManager = myNewsManager
        newsRecyclerView.adapter = myNewsAdapter
        newsRecyclerView.setPadding(0,0,0,50)

        //Adding pager behaviour
        val snapHelper = PagerSnapHelper()
        newsRecyclerView.onFlingListener = null //<-- We add this line to avoid the app crashing when returning from the background
        snapHelper.attachToRecyclerView(newsRecyclerView)
        newsRecyclerView.addItemDecoration(CirclePagerIndicatorDecoration())


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            newsRecyclerView.addItemDecoration(CirclePagerIndicatorDecoration())
        }

        //Adding buttons listeners
        initArrowsListeners(myNewsManager)

        subscribeToPosts()
    }

    private fun subscribeToPosts() {
        mMagicLineViewModel.getPosts(getAPILang(requireContext())).observe(this, androidx.lifecycle.Observer {
            myNewsAdapter.loadItems(toNewsModel(it))
            myNewsAdapter.notifyDataSetChanged()})

        mMagicLineViewModel.getDonations().observe(this, androidx.lifecycle.Observer { donation ->
            if (donation != null) {
                recaudats_num.text = getDonationsByCity(donation).addCurrency()
            }
        })

        mMagicLineViewModel.getTotalParticipants().observe(this, androidx.lifecycle.Observer { participants ->
            participants?.let {
                participants_num.text = it.totalParticipants.toString().addThousandsSeparator()
            }
        })
    }

    private fun toNewsModel(list: List<PostsItem>): List<NewsModel> {
        val news : MutableList<NewsModel> = mutableListOf()
        val onClickListener: (NewsModel) -> Unit = { newsModel ->
            (activity as AppCompatActivity).transitionWithModalAnimation(
                    requireContext(),
                    fragment = DetailFragment.newInstance(newsModel.detailModel),
                    analyticsScreen = TrackingUtils.Screens.NewsDetail
            )
        }

        for (item in list) {
            val detailModel = DetailModel(
                    title = item.post.title.toString(),
                    subtitle = item.post.teaser.toString(),
                    textBody = item.post.text.toString(),
                    listToolbarImg = emptyList(),
                    listPostImg = item.postImages,
                    hasToolbarImg = false,
                    link = item.post.url.toString()
            )

            news.add(NewsModel(
                    title = detailModel.title,
                    subtitle = detailModel.subtitle,
                    detailModel = detailModel,
                    listener = onClickListener
            ))
        }
        return news
    }

    private fun initArrowsListeners(mLayoutManager: LinearLayoutManager) {

        right_arrow_relative.setOnClickListener {
            var totalItemCount = newsRecyclerView.adapter?.itemCount ?: 0

            if (totalItemCount < 0) return@setOnClickListener

            var lastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition()

            if (lastVisibleItemIndex >= totalItemCount) return@setOnClickListener
            if(lastVisibleItemIndex >= totalItemCount - 1) {
                mLayoutManager.smoothScrollToPosition(newsRecyclerView, null, 0)
            }
            else {
                mLayoutManager.smoothScrollToPosition(newsRecyclerView, null, lastVisibleItemIndex + 1)
            }
        }

        left_arrow_relative.setOnClickListener {
            val totalItemCount = newsRecyclerView.adapter?.itemCount ?:0

            if (totalItemCount < 0) return@setOnClickListener

            val lastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition()

            if(lastVisibleItemIndex <= 0) {
                mLayoutManager.smoothScrollToPosition(newsRecyclerView, null, newsRecyclerView.adapter?.itemCount ?:0)
            }
            else {
                mLayoutManager.smoothScrollToPosition(newsRecyclerView, null, lastVisibleItemIndex - 1)
            }
        }
    }

    private fun initAfonsListeners() {

        val dataModelEssential = DetailModel(
                title = getString(R.string.essentials_title),
                subtitle = "",
                textBody = getString(R.string.essentials_body),
                link = getString(R.string.essentials_viewOnWeb),
                isBlack = true,
                listToolbarImg = listOf(R.drawable.imprescindibles),
                hasToolbarImg = false,
                titleToolbar = getString(R.string.title_toolbar_imprs))
        val dataModelDestiny = DetailModel(
                title = getString(R.string.donations_title),
                subtitle = "",
                textBody = getString(R.string.donations_body),
                link = getString(R.string.donations_viewOnWeb),
                isBlack = true,
                listToolbarImg = listOf(R.drawable.destidelfons, R.drawable.sliderimage2),
                hasToolbarImg = false)
        val dataModelSantJoan = DetailModel(
                title = getString(R.string.sjd_title),
                subtitle = getString(R.string.sjd_subtitle),
                textBody = getString(R.string.sjd_body),
                link = getString(R.string.sjd_viewOnWeb),
                isBlack = true,
                listToolbarImg = listOf(R.drawable.sliderimage3, R.drawable.laboratori),
                hasToolbarImg = false)

        info_essentials_button.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(requireContext(), DetailFragment.newInstance(dataModelEssential), analyticsScreen = TrackingUtils.Screens.Detail)
        }

        info_donations_destiny_button.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(requireContext(), DetailFragment.newInstance(dataModelDestiny), analyticsScreen = TrackingUtils.Screens.Detail)
        }

        info_sjd_button.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(requireContext(), DetailFragment.newInstance(dataModelSantJoan), analyticsScreen = TrackingUtils.Screens.Detail)
        }
    }

    private fun getDonationsByCity(donation : DonationsDBModel) : Double {
        return when (getFlavor()) {
            BARCELONA -> donation.donationsBcn!!.toDouble()
            MALLORCA -> donation.donationsMll!!.toDouble()
            VALENCIA -> donation.donationsVal!!.toDouble()
            else -> 0.0
        }
    }
}
