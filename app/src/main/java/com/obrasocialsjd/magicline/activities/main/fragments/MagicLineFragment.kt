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
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.R.id.*
import com.obrasocialsjd.magicline.activities.main.adapters.NewsAdapter
import com.obrasocialsjd.magicline.activities.main.general.MainActivity
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
import kotlinx.android.synthetic.main.layout_countdown_top.view.*
import kotlinx.android.synthetic.main.layout_mes_que.*
import kotlinx.android.synthetic.main.layout_news.*
import kotlinx.android.synthetic.main.layout_news.view.*
import java.util.*

class MagicLineFragment : BaseFragment() {

    private lateinit var mMagicLineViewModel: MagicLineViewModel
    private lateinit var myNewsAdapter: NewsAdapter
    private lateinit var myNewsManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = MagicLineRepositoryImpl(MagicLineDB.getDatabase(requireActivity().applicationContext))
        val factory = MagicLineViewModelFactory(requireActivity().application, repository)
        mMagicLineViewModel = ViewModelProviders.of(this, factory).get(MagicLineViewModel::class.java)
    }
    //Setting the corresponding view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  = inflater.inflate(R.layout.fragment_magic_line, container, false)

        //Init TextViews, etc
        val txtArray = initWidgets(view)

        initCountDown(txtArray)

        initNewsRecycler(view)

        return view
    }

    override fun onStart() {
        super.onStart()

        subscribeToPosts()

        initArrowsListeners(myNewsManager)

        initMoreInfoMLListener()

        initMesQueListeners()

        initAfonsListeners()

        initRrss()
    }

    private fun initRrss() {
        val urlFacebook = getString(R.string.url_facebook)
        val urlInstagram = getString(R.string.url_instagram)
        val urlTwitter = getString(R.string.url_twitter)

        rrssView.fbListener = { activity?.callIntent(urlFacebook) }
        rrssView.instaListener = { activity?.callIntent(urlInstagram) }
        rrssView.twitterListener = { activity?.callIntent(urlTwitter) }
    }

    private fun initMesQueListeners() {

        btnDonateTeam.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(DonationsFragment.newInstance())
        }

        btnRequestDonates.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(InviteFriendsFragment.newInstance())
        }

        btnBrainStorm.setOnClickListener { (activity as MainActivity).callIntent(getString(R.string.pdf_donations_collecting_guide)) }
    }

    private fun initMoreInfoMLListener() {
        moreInfoML.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(fragment = MoreInfoMLFragment.newInstance())
        }
    }

    private fun initWidgets(view : View): Array<TextView> {
        return arrayOf(view.countdownDays, view.countdown_hores, view.countdownMin, view.countdownSec)
    }

    private fun initCountDown(txtDies: Array<TextView>) {
        val currentTime: Long = Calendar.getInstance().timeInMillis
        val dateLong: Long = resources.getString(R.string.magicLineDate).toLong()
        val diff: Long = dateLong - currentTime
        MyCounter(diff, 1000, txtDies).start()
    }

    private fun initNewsRecycler(view : View) {
        myNewsManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        myNewsAdapter = NewsAdapter(ArrayList())

        view.newsRecyclerView.layoutManager = myNewsManager
        view.newsRecyclerView.adapter = myNewsAdapter
        view.newsRecyclerView.setPadding(0,0,0,50)

        //Adding pager behaviour
        val snapHelper = PagerSnapHelper()
        view.newsRecyclerView.onFlingListener = null //<-- We add this line to avoid the app crashing when returning from the background
        snapHelper.attachToRecyclerView(newsRecyclerView)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            view.newsRecyclerView.addItemDecoration(CirclePagerIndicatorDecoration())
        }
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
            (activity as AppCompatActivity).transitionWithModalAnimation(DetailFragment.newInstance(newsModel.detailModel))
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

        news_right_arrow.setOnClickListener {
            val totalItemCount = newsRecyclerView.adapter?.itemCount ?: 0

            if (totalItemCount < 0) return@setOnClickListener

            val lastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition()

            if (lastVisibleItemIndex >= totalItemCount) return@setOnClickListener

            mLayoutManager.smoothScrollToPosition(newsRecyclerView, null, lastVisibleItemIndex + 1)
        }

        news_left_arrow.setOnClickListener {
            val totalItemCount = newsRecyclerView.adapter?.itemCount ?:0

            if (totalItemCount < 0) return@setOnClickListener

            val lastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition()

            if (lastVisibleItemIndex <= 0) return@setOnClickListener

            mLayoutManager.smoothScrollToPosition(newsRecyclerView, null, lastVisibleItemIndex - 1)
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
                listToolbarImg = listOf(R.drawable.destidelfons, R.drawable.sliderimage2, R.drawable.sliderimage3, R.drawable.laboratori),
                hasToolbarImg = false)
        val dataModelSantJoan = DetailModel(
                title = getString(R.string.sjd_title),
                subtitle = getString(R.string.sjd_subtitle),
                textBody = getString(R.string.sjd_body),
                link = getString(R.string.sjd_viewOnWeb),
                isBlack = true,
                listToolbarImg = listOf(R.drawable.sliderimage3, R.drawable.sliderimage2, R.drawable.laboratori, R.drawable.destidelfons),
                hasToolbarImg = false)

        info_essentials_button.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(DetailFragment.newInstance(dataModelEssential))
        }

        info_donations_destiny_button.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(DetailFragment.newInstance(dataModelDestiny))
        }

        info_sjd_button.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(DetailFragment.newInstance(dataModelSantJoan))
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
