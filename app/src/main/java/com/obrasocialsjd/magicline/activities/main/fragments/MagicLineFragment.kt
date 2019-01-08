package com.obrasocialsjd.magicline.activities.main.fragments

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
import kotlinx.android.synthetic.main.layout_a_fons.*
import kotlinx.android.synthetic.main.layout_countdown.*
import kotlinx.android.synthetic.main.layout_mes_que.*
import kotlinx.android.synthetic.main.layout_news.*
import kotlinx.android.synthetic.main.layout_recaudats_participants.*
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

        initStaticContent()

        initMoreInfoMLListener()

        initNewsRecycler()

        initMesQueListeners()

        initAfonsListeners()

    }

    private fun initMesQueListeners() {

        btnDonateTeam.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(DonationsFragment.newInstance())
        }

        btnRequestDonates.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(InviteFriendsFragment.newInstance())
        }

        btnBrainStorm.setOnClickListener {
            if (activity is MainActivity) (activity as MainActivity).callIntent(URL_IDEAS_GUIDE)
        }
    }

    private fun initMoreInfoMLListener() {
        moreInfoML.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(fragment = MoreInfoMLFragment.newInstance(), showShareView = true)
        }
    }

    private fun initStaticContent() {
        participants_num.text = getString(R.string.cityParticipants).addThousandsSeparator()
    }

    private fun initWidgets(): Array<TextView> {

        //cursa date
        dateCursaString = getString(R.string.cursa_date)

        return arrayOf(countdown_dies, countdown_hores, countdown_min, countdown_seg)
    }

    private fun initCountDown(txtDies: Array<TextView>) {
        //Utilitzem el formatter per aconseguir l'objecte Date
        val formatter = SimpleDateFormat("dd.MM.yyyy, HH:mm")

        //data actual y data de la cursa
        val currentTime: Date = Calendar.getInstance().time
        val dateCursa: Date = formatter.parse(dateCursaString)

        //pasem a long les dates
        val currentLong: Long = currentTime.time
        val cursaLong: Long = dateCursa.time

        //trobem el temps restant en long
        val diff: Long = cursaLong - currentLong

        MyCounter(diff, 1000, txtDies).start()

    }

    private fun initNewsRecycler() {
        val myNewsManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        myNewsAdapter = NewsAdapter(ArrayList())

        newsRecyclerView.layoutManager = myNewsManager
        newsRecyclerView.adapter = myNewsAdapter
        newsRecyclerView.setPadding(0,0,0,50)

        //Adding pager behaviour
        val snapHelper = PagerSnapHelper()
        newsRecyclerView.onFlingListener = null //<-- We add this line to avoid the app crashing when returning from the background
        snapHelper.attachToRecyclerView(newsRecyclerView)
        newsRecyclerView.addItemDecoration(CirclePagerIndicatorDecoration())

        //Adding the page indicators
        // TODO re-DO news slider

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
                subtitle = "",
                textBody = getString(R.string.essentials_body),
                link = getString(R.string.essentials_viewOnWeb),
                isBlack = false,
               // toolbarImg = R.drawable.imprescindibles,
                hasToolbarImg = true)
        val dataModelDestiny = DetailModel(
                title = getString(R.string.donations_title),
                subtitle = "",
                textBody = getString(R.string.donations_body),
                link = getString(R.string.donations_viewOnWeb),
                isBlack = false,
              //  toolbarImg = R.drawable.destidelfons,
                hasToolbarImg = true)
        val dataModelSantJoan = DetailModel(
                title = getString(R.string.sjd_title),
                subtitle = getString(R.string.sjd_subtitle),
                textBody = getString(R.string.sjd_body),
                link = getString(R.string.sjd_viewOnWeb),
                isBlack = false,
              //  toolbarImg = R.drawable.laboratori,
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

    private fun getDonationsByCity(donation : DonationsDBModel) : Double {
        return when (getFlavor()) {
            BARCELONA -> donation.donationsBcn!!.toDouble()
            MALLORCA -> donation.donationsMll!!.toDouble()
            VALENCIA -> donation.donationsVal!!.toDouble()
            else -> 0.0
        }
    }
}
