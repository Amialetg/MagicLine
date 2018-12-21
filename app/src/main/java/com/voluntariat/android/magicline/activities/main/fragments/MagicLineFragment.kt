package com.voluntariat.android.magicline.activities.main.fragments

import android.content.Intent
import android.net.Uri
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
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.R.drawable.about_us
import com.voluntariat.android.magicline.activities.main.adapters.NewsAdapter
import com.voluntariat.android.magicline.activities.main.otherui.CirclePagerIndicatorDecoration
import com.voluntariat.android.magicline.data.MagicLineRepositoryImpl
import com.voluntariat.android.magicline.data.Result
import com.voluntariat.android.magicline.data.models.posts.PostsItem
import com.voluntariat.android.magicline.db.MagicLineDB
import com.voluntariat.android.magicline.models.DetailModel
import com.voluntariat.android.magicline.models.NewsModel
import com.voluntariat.android.magicline.utils.MyCounter
import com.voluntariat.android.magicline.utils.URL_IDEAS_GUIDE
import com.voluntariat.android.magicline.utils.toEuro
import com.voluntariat.android.magicline.utils.transitionWithModalAnimation
import com.voluntariat.android.magicline.viewModel.MagicLineViewModel
import com.voluntariat.android.magicline.viewModel.MagicLineViewModelFactory
import kotlinx.android.synthetic.main.layout_a_fons.*
import kotlinx.android.synthetic.main.layout_countdown.*
import kotlinx.android.synthetic.main.layout_mes_que.*
import kotlinx.android.synthetic.main.layout_news.*
import kotlinx.android.synthetic.main.layout_recaudats_participants.*
import kotlinx.android.synthetic.main.layout_rrss.*
import java.text.SimpleDateFormat
import java.util.*

class MagicLineFragment : BaseFragment() {


    private lateinit var mMagicLineViewModel: MagicLineViewModel

    private lateinit var dateCursaString: String
    private lateinit var myNewsAdapter: NewsAdapter

    //Programming section widgets
    lateinit var progRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var repository = MagicLineRepositoryImpl(MagicLineDB.getDatabase(requireActivity().applicationContext))
        val factory = MagicLineViewModelFactory(requireActivity().application, repository)
        mMagicLineViewModel = ViewModelProviders.of(this, factory).get(MagicLineViewModel::class.java)

    }

    //Setting the corresponding view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_magic_line, container, false)
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

        initRRSSListeners()

    }

    private fun initMesQueListeners() {

        btnDonateTeam.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(DonationsFragment.newInstance())
        }

        btnRequestDonates.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(InviteFriendsFragment.newInstance())
        }

        btnBrainStorm.setOnClickListener {
            callIntent(URL_IDEAS_GUIDE)
        }
    }

    private fun initMoreInfoMLListener() {
        moreInfoML.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(MoreInfoMLFragment.newInstance())
        }
    }

    private fun initWidgets(): Array<TextView> {

        //cursa date
        dateCursaString = getString(R.string.cursa_date)

        return arrayOf(countdown_dies, countdown_hores, countdown_min, countdown_seg)
    }

    private fun initCountDown(txtDies: Array<TextView>) {

        //Utilitzem el formatter per aconseguir l'objecte Date
        var formatter = SimpleDateFormat("dd.MM.yyyy, HH:mm")

        //Data actual y data de la cursa
        var currentTime: Date = Calendar.getInstance().time
        var dateCursa: Date = formatter.parse(dateCursaString)

        //pasem a long les dates
        var currentLong: Long = currentTime.time
        var cursaLong: Long = dateCursa.time

        //trobem el temps restant en long
        var diff: Long = cursaLong - currentLong

        MyCounter(diff, 1000, txtDies).start()

    }

    private fun initNewsRecycler() {
        val myNewsManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        myNewsAdapter = NewsAdapter(ArrayList())

        newsRecyclerView.layoutManager = myNewsManager
        newsRecyclerView.adapter = myNewsAdapter

        //Adding pager behaviour
        val snapHelper = PagerSnapHelper()
        newsRecyclerView.onFlingListener = null //<-- We add this line to avoid the app crashing when returning from the background
        snapHelper.attachToRecyclerView(newsRecyclerView)
        newsRecyclerView.addItemDecoration(CirclePagerIndicatorDecoration())

        //Adding the page indicators
        // TODO re-DO news slider

        //Adding buttons listeners
        initArrowsListeners(myNewsManager)
        val repo = MagicLineRepositoryImpl(MagicLineDB.getDatabase(requireActivity().applicationContext))
        repo.authenticate(
                "apiml",
                "4p1ml2018"
        ) { result -> when (result) {
            is Result.Success -> subscribeToPosts()
        } }

    }

    private fun subscribeToPosts() {
        mMagicLineViewModel.getPosts().observe(this, androidx.lifecycle.Observer {
            myNewsAdapter.loadItems(toNewsModel(it))
            myNewsAdapter.notifyDataSetChanged()})

        mMagicLineViewModel.getDonations().observe(this, androidx.lifecycle.Observer { donations ->
            // TODO refactor
            var donationText = 0.0
            for (donation in donations) {
                if (donation.donationsBcn != null && donation.donationsBcn.isNotEmpty() && donation.donationsBcn?.toDouble()!! > donationText) {
                    donationText = donation.donationsBcn?.toDouble()
                }
            }
            recaudats_num.text = donationText.toEuro()
        })
    }

    private fun toNewsModel(list: List<PostsItem>): List<NewsModel> {
        val news : MutableList<NewsModel> = mutableListOf()
        for (item in list) {
            news.add(NewsModel(title = item.post.title, description = item.post.text))
        }
        return news
    }

    private fun initArrowsListeners(mLayoutManager: LinearLayoutManager) {

        right_arrow_relative.setOnClickListener {
            val totalItemCount = newsRecyclerView.adapter?.itemCount ?: 0

            if (totalItemCount < 0) return@setOnClickListener

            val lastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition()

            if (lastVisibleItemIndex >= totalItemCount) return@setOnClickListener

            mLayoutManager.smoothScrollToPosition(newsRecyclerView, null, lastVisibleItemIndex + 1)
        }

        left_arrow_relative.setOnClickListener {
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
                subtitle = getString(R.string.essentials_subtitle),
                textBody = getString(R.string.essentials_body),
                link = getString(R.string.essentials_viewOnWeb))
        val dataModelDestiny = DetailModel(
                title = getString(R.string.donations_title),
                subtitle = getString(R.string.donations_subtitle),
                textBody = getString(R.string.donations_body),
                link = getString(R.string.donations_viewOnWeb))
        val dataModelSantJoan = DetailModel(
                title = getString(R.string.sjd_title),
                subtitle = getString(R.string.sjd_subtitle),
                textBody = getString(R.string.sjd_body),
                link = getString(R.string.sjd_viewOnWeb),
                toolbarImg = about_us,
                hasToolbarImg = true)

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

    private fun initRRSSListeners() {

        val urlFacebook = getString(R.string.url_facebook)
        val urlGoogle = getString(R.string.url_google)
        val urlTwitter = getString(R.string.url_twitter)


        fb_button.setOnClickListener {
            callIntent(urlFacebook)
        }

        insta_button.setOnClickListener {
            callIntent(urlGoogle)
        }

        twitter_button.setOnClickListener {
            callIntent(urlTwitter)
        }
    }

    private fun callIntent(url: String) {
        var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.requireContext().startActivity(intent)
    }
}
