package com.voluntariat.android.magicline.activities.main.fragments

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getDrawable

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import java.util.*
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.voluntariat.android.magicline.*
import com.voluntariat.android.magicline.R.drawable.about_us
import com.voluntariat.android.magicline.R.string.lorem_ipsum
import com.voluntariat.android.magicline.Utils.MyCounter
import com.voluntariat.android.magicline.activities.main.MainActivity
import com.voluntariat.android.magicline.activities.main.adapters.NewsAdapter
import com.voluntariat.android.magicline.activities.main.adapters.ProgrammingAdapter
import com.voluntariat.android.magicline.models.DetailModel
import com.voluntariat.android.magicline.models.NewsModel
import com.voluntariat.android.magicline.models.ProgrammingModel
import kotlinx.android.synthetic.main.layout_a_fons.*
import kotlinx.android.synthetic.main.layout_countdown.*
import kotlinx.android.synthetic.main.layout_mes_que.*
import kotlinx.android.synthetic.main.layout_news.*
import kotlinx.android.synthetic.main.layout_recaudats_participants.*
import kotlinx.android.synthetic.main.layout_rrss.*
import java.text.SimpleDateFormat


class MagicLineFragment : Fragment() {

    private lateinit var dateCursaString: String

    //Programming section widgets
    lateinit var progRecyclerView: RecyclerView

    //Setting the corresponding view
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, DonationsFragment())
            transaction.addToBackStack("donateTeam")
            transaction.commit()
        }
    }

    private fun initMoreInfoMLListener() {
        moreInfoML.setOnClickListener {
            val transaction = activity.supportFragmentManager.beginTransaction()
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

    private fun initProgrammingCards() {

        //data Test
        val events = ArrayList<ProgrammingModel>()
        events.add(ProgrammingModel("TOT EL DIA", "Museus Oberts"))
        events.add(ProgrammingModel("10:30", "Concert"))
        events.add(ProgrammingModel("Avui", "Dinar"))
        events.add(ProgrammingModel("Avui", "Dinar"))


        //Setting up the adapter and the layout manager for the recycler view
        progRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)
        val adapter = ProgrammingAdapter(events)
        progRecyclerView.adapter = adapter
    }

    private fun initNewsRecycler() {
        val dataSet = arrayOf(NewsModel("Nou event en la programació", getString(lorem_ipsum)), NewsModel("Segon event en la programació", "In recent years people have realized the importance of proper diet and exercise, and recent surveys show that over the  otal ruta."), NewsModel("Tercer event en la programació", "In recent years people have realized the importance of proper diet and exercise, and recent surveys show that over the  otal ruta."), NewsModel("Quart event en la programació", "In recent years people have realized the importance of proper diet and exercise, and recent surveys show that over the  otal ruta."))

        val myNewsManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val myNewsAdapter = NewsAdapter(dataSet)

        news_recycler.layoutManager = myNewsManager
        news_recycler.adapter = myNewsAdapter

        //Adding pager behaviour
        val snapHelper = PagerSnapHelper()
        news_recycler.onFlingListener = null //<-- We add this line to avoid the app crashing when returning from the background
        snapHelper.attachToRecyclerView(news_recycler)

        //Adding the page indicators
        news_pager_indicator.setRecyclerView(news_recycler)

        //Adding buttons listeners
        initArrowsListeners(myNewsManager)
    }

    private fun initArrowsListeners(mLayoutManager: LinearLayoutManager) {

        right_arrow_relative.setOnClickListener {
            val totalItemCount = news_recycler.adapter.itemCount

            if (totalItemCount < 0) return@setOnClickListener

            val lastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition()

            if (lastVisibleItemIndex >= totalItemCount) return@setOnClickListener

            mLayoutManager.smoothScrollToPosition(news_recycler, null, lastVisibleItemIndex + 1)
        }

        left_arrow_relative.setOnClickListener {
            val totalItemCount = news_recycler.adapter.itemCount

            if (totalItemCount < 0) return@setOnClickListener

            val lastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition()

            if (lastVisibleItemIndex <= 0) return@setOnClickListener

            mLayoutManager.smoothScrollToPosition(news_recycler, null, lastVisibleItemIndex - 1)
        }
    }

    private fun initAfonsListeners() {

        val dataModelEssential = DetailModel(title = getString(R.string.essentials_title), subtitle = getString(R.string.essentials_subtitle), textBody = getString(R.string.essentials_body), link = getString(R.string.essentials_viewOnWeb), toolbarImg = getDrawable(context, about_us))
        val dataModelDestiny = DetailModel(title = getString(R.string.donations_title), subtitle = getString(R.string.donations_subtitle), textBody = getString(R.string.donations_body), link = getString(R.string.donations_viewOnWeb), toolbarImg = getDrawable(context, about_us))
        val dataModelSantJoan = DetailModel(title = getString(R.string.sjd_title), subtitle = getString(R.string.sjd_subtitle), textBody = getString(R.string.sjd_body), link = getString(R.string.sjd_viewOnWeb), toolbarImg = getDrawable(context, about_us))

        info_essentials_button.setOnClickListener {
            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, DetailFragment.newInstance(dataModelEssential))
            transaction.addToBackStack("infoEssentialsButton")
            transaction.commit()
        }

        info_donations_destiny_button.setOnClickListener {
            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, DetailFragment.newInstance(dataModelDestiny))
            transaction.addToBackStack("infoDestinyButton")
            transaction.commit()
        }

        info_sjd_button.setOnClickListener {
            val transaction = activity.supportFragmentManager.beginTransaction()
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
        context.startActivity(intent)
    }

//    class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
//        override fun getItemOffsets(outRect: Rect, view: View,
//                                    parent: RecyclerView, state: RecyclerView.State) {
//            with(outRect) {
//
//                if (parent.getChildAdapterPosition(view) == parent.adapter.itemCount - 1) {
//                    right = spaceHeight * 2
//                    left = spaceHeight
//                } else if (parent.getChildAdapterPosition(view) == 0) {
//                    left = spaceHeight * 2
//                    right = spaceHeight
//                } else {
//                    left = spaceHeight
//                    right = spaceHeight
//                }
//            }
//        }
//    }
}
