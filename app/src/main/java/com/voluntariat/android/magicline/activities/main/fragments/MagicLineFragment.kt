package com.voluntariat.android.magicline.activities.main.fragments

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import java.util.*
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voluntariat.android.magicline.*
import com.voluntariat.android.magicline.activities.main.adapters.CountdownPagerAdapter
import com.voluntariat.android.magicline.activities.main.adapters.NewsAdapter
import com.voluntariat.android.magicline.activities.main.adapters.ProgrammingAdapter
import com.voluntariat.android.magicline.models.NewsModel
import com.voluntariat.android.magicline.models.ProgrammingModel


class MagicLineFragment : Fragment() {

    //Countdown - recaudats widgets
    lateinit var viewPager: ViewPager
    lateinit var viewPagerIndicator: com.kingfisher.easyviewindicator.AnyViewIndicator

    //Programming section widgets
    lateinit var progRecyclerView: RecyclerView

    //News section widgets
    lateinit var newsRecyclerView: RecyclerView
    lateinit var leftArrowView: RelativeLayout
    lateinit var rightArrowView: RelativeLayout
    lateinit var recyclerViewIndicator: com.kingfisher.easyviewindicator.RecyclerViewIndicator

    //RRSS views
    lateinit var facebookView : View
    lateinit var googleView : View
    lateinit var twitterView : View

    /*
     * Setting the corresponding view
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_magic_line, container,  false)
    }

    /*
     * Once the view is ready we can initialize all components
     */

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

    }

    override fun onStart() {
        super.onStart()

        //Init TextViews, etc
        initWidgets()

        initCountdownPV(viewPager)

        initProgrammingCards()

        initNewsRecycler()

        initRRSSListeners()

    }

    private fun initWidgets(){
        //Countdown - recaudats view pager
        viewPager = view!!.findViewById(R.id.principal_vp)
        viewPagerIndicator=view!!.findViewById(R.id.view_pager_indicator)

        //Programming cards
        progRecyclerView= view!!.findViewById(R.id.rv)
        progRecyclerView.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.margin_programming).toInt()))

        //News
        newsRecyclerView=view!!.findViewById(R.id.news_recycler)
        leftArrowView=view!!.findViewById(R.id.left_arrow_relative)
        rightArrowView=view!!.findViewById(R.id.right_arrow_relative)
        recyclerViewIndicator=view!!.findViewById(R.id.news_pager_indicator)

        //RRSS

        facebookView = view!!.findViewById<View>(R.id.facebook)
        googleView = view!!.findViewById<View>(R.id.google)
        twitterView = view!!.findViewById<View>(R.id.twitter)


    }

    private fun initCountdownPV(viewPager: ViewPager){
        /*
         * We use childFragmentManager instead of supportFragmentManager because
         * we are using a fragment inside a fragment
        */
        val adapter = CountdownPagerAdapter(childFragmentManager)

        viewPager.adapter=adapter

        //Number of fragments to scroll
        viewPagerIndicator.setItemCount(adapter.count)

        //Where does it start
        viewPagerIndicator.setCurrentPosition(0)

        //Change when scroll
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                viewPagerIndicator.setCurrentPosition(viewPager.currentItem)
            }

            override fun onPageSelected(position: Int) {
                // Check if this is the page you want.
            }
        })

    }

    private fun initProgrammingCards(){

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
        val dataSet = arrayOf(NewsModel("Nou event en la programació", "In recent years people have realized the importance of proper diet and exercise, and recent surveys show that over the  otal ruta."), NewsModel("Segon event en la programació", "In recent years people have realized the importance of proper diet and exercise, and recent surveys show that over the  otal ruta."), NewsModel("Tercer event en la programació", "In recent years people have realized the importance of proper diet and exercise, and recent surveys show that over the  otal ruta."), NewsModel("Quart event en la programació", "In recent years people have realized the importance of proper diet and exercise, and recent surveys show that over the  otal ruta."))

        val myNewsManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val myNewsAdapter = NewsAdapter(dataSet)

        newsRecyclerView.layoutManager = myNewsManager
        newsRecyclerView.adapter = myNewsAdapter

        //Adding pager behaviour
        val snapHelper = PagerSnapHelper()
        newsRecyclerView.onFlingListener = null //<-- We add this line to avoid the app crashing when returning from the background
        snapHelper.attachToRecyclerView(newsRecyclerView)

        //Adding the page indicators
        recyclerViewIndicator.setRecyclerView(newsRecyclerView)

        //Adding buttons listeners
        initArrowsListeners(myNewsManager)
    }

    private fun initArrowsListeners(mLayoutManager: LinearLayoutManager){
        
        rightArrowView.setOnClickListener{
            val totalItemCount= newsRecyclerView.adapter.itemCount

            if(totalItemCount<0) return@setOnClickListener

            val lastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition()

            if(lastVisibleItemIndex>=totalItemCount) return@setOnClickListener

            mLayoutManager.smoothScrollToPosition(newsRecyclerView, null, lastVisibleItemIndex+1)
        }
        leftArrowView.setOnClickListener {
            val totalItemCount= newsRecyclerView.adapter.itemCount

            if(totalItemCount<0) return@setOnClickListener

            val lastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition()

            if(lastVisibleItemIndex<=0) return@setOnClickListener

            mLayoutManager.smoothScrollToPosition(newsRecyclerView, null, lastVisibleItemIndex-1)
        }
    }

    private fun initRRSSListeners(){

        val urlFacebook = getString(R.string.url_facebook)
        val urlGoogle = getString(R.string.url_google)
        val urlTwitter = getString(R.string.url_twitter)


        facebookView.setOnClickListener{
            callIntent(urlFacebook)
        }

        googleView.setOnClickListener{
            callIntent(urlGoogle)
        }

        twitterView.setOnClickListener{
            callIntent(urlTwitter)
        }


    }

    private fun callIntent(url : String){

        var intent : Intent

        intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)

    }

    class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View,
                                    parent: RecyclerView, state: RecyclerView.State) {
            with(outRect) {

                if(parent.getChildAdapterPosition(view) == parent.adapter.itemCount-1){
                    right = spaceHeight*2
                    left = spaceHeight
                }
                else if(parent.getChildAdapterPosition(view) == 0){
                    left = spaceHeight*2
                    right = spaceHeight
                }
                else{
                    left =  spaceHeight
                    right = spaceHeight
                }
            }
        }
    }

}
