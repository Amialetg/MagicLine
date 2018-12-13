package com.voluntariat.android.magicline.activities.main.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProviders
import com.voluntariat.android.magicline.*
import com.voluntariat.android.magicline.R.drawable.about_us
import com.voluntariat.android.magicline.R.string.lorem_ipsum
import com.voluntariat.android.magicline.utils.MyCounter
import com.voluntariat.android.magicline.utils.URL_IDEAS_GUIDE
import com.voluntariat.android.magicline.activities.main.adapters.NewsAdapter
import com.voluntariat.android.magicline.data.MagicLineRepositoryImpl
import com.voluntariat.android.magicline.db.MagicLineDB
import com.voluntariat.android.magicline.models.DetailModel
import com.voluntariat.android.magicline.models.NewsModel
import com.voluntariat.android.magicline.viewModel.MagicLineViewModel
import com.voluntariat.android.magicline.viewModel.MagicLineViewModelFactory
import kotlinx.android.synthetic.main.layout_a_fons.*
import kotlinx.android.synthetic.main.layout_countdown.*
import kotlinx.android.synthetic.main.layout_mes_que.*
import kotlinx.android.synthetic.main.layout_news.*
import kotlinx.android.synthetic.main.layout_recaudats_participants.*
import kotlinx.android.synthetic.main.layout_rrss.*
import java.text.SimpleDateFormat


class MagicLineFragment : androidx.fragment.app.Fragment() {


    private lateinit var mMagicLineViewModel: MagicLineViewModel

    private lateinit var dateCursaString: String

    //Programming section widgets
    lateinit var progRecyclerView: androidx.recyclerview.widget.RecyclerView

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
            val transaction = this.requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, DonationsFragment())
            transaction.addToBackStack("donateTeam")
            transaction.commit()
        }

        btnRequestDonates.setOnClickListener {
            val transaction = this.requireActivity().supportFragmentManager.beginTransaction()
            //todo: uncomment when merge the branch multi lang
//            transaction.replace(R.id.frame_layout, InviteFriendsFragment())
            transaction.addToBackStack("donateTeam")
            transaction.commit()
        }

        btnBrainStorm.setOnClickListener {
            callIntent(URL_IDEAS_GUIDE)
        }
    }

    private fun initMoreInfoMLListener() {
        moreInfoML.setOnClickListener {
            val transaction = this.requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, MoreInfoMLFragment())
            transaction.addToBackStack("infoEssentialsButton")
            transaction.commit()
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
        val myNewsManager = androidx.recyclerview.widget.LinearLayoutManager(activity, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
        val myNewsAdapter = NewsAdapter()

        news_recycler.layoutManager = myNewsManager
        news_recycler.adapter = myNewsAdapter

        //Adding pager behaviour
        val snapHelper = androidx.recyclerview.widget.PagerSnapHelper()
        news_recycler.onFlingListener = null //<-- We add this line to avoid the app crashing when returning from the background
        snapHelper.attachToRecyclerView(news_recycler)

        //Adding the page indicators
        // TODO re-DO news slider

        //Adding buttons listeners
        initArrowsListeners(myNewsManager)

        mMagicLineViewModel.posts.observe(this, androidx.lifecycle.Observer {
            myNewsAdapter.loadItems(it ?: emptyList())
            myNewsAdapter.notifyDataSetChanged()})
    }

    private fun initArrowsListeners(mLayoutManager: androidx.recyclerview.widget.LinearLayoutManager) {

        right_arrow_relative.setOnClickListener {
            val totalItemCount = news_recycler.adapter?.itemCount ?: 0

            if (totalItemCount < 0) return@setOnClickListener

            val lastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition()

            if (lastVisibleItemIndex >= totalItemCount) return@setOnClickListener

            mLayoutManager.smoothScrollToPosition(news_recycler, null, lastVisibleItemIndex + 1)
        }

        left_arrow_relative.setOnClickListener {
            val totalItemCount = news_recycler.adapter?.itemCount ?:0

            if (totalItemCount < 0) return@setOnClickListener

            val lastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition()

            if (lastVisibleItemIndex <= 0) return@setOnClickListener

            mLayoutManager.smoothScrollToPosition(news_recycler, null, lastVisibleItemIndex - 1)
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
            val transaction = this.requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, DetailFragment.newInstance(dataModelEssential))
            transaction.addToBackStack("infoEssentialsButton")
            transaction.commit()
        }

        info_donations_destiny_button.setOnClickListener {
            val transaction = this.requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, DetailFragment.newInstance(dataModelDestiny))
            transaction.addToBackStack("infoDestinyButton")
            transaction.commit()
        }

        info_sjd_button.setOnClickListener {
            val transaction = this.requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, DetailFragment.newInstance(dataModelSantJoan))
            transaction.addToBackStack("infoSjdButton")
            transaction.commit()
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
        var intent: Intent
        intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.requireContext().startActivity(intent)
    }
}
