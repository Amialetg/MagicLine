package com.obrasocialsjd.magicline.activities.main.general

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.analytics.FirebaseAnalytics
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.activities.main.adapters.NewsAdapter
import com.obrasocialsjd.magicline.activities.main.fragments.*
import com.obrasocialsjd.magicline.data.MagicLineRepositoryImpl
import com.obrasocialsjd.magicline.data.models.posts.PostsItem
import com.obrasocialsjd.magicline.db.MagicLineDB
import com.obrasocialsjd.magicline.models.*
import com.obrasocialsjd.magicline.utils.*
import com.obrasocialsjd.magicline.viewModel.MagicLineViewModel
import com.obrasocialsjd.magicline.viewModel.MagicLineViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable
import java.util.*
import android.net.ConnectivityManager
import android.provider.Settings.Global.getString

class MainActivity : BaseActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var currentFragment: Fragment
    private lateinit var scheduleModel: List<ScheduleGeneralModel>
    private lateinit var mMagicLineViewModel: MagicLineViewModel
    private lateinit var myNewsAdapter: NewsAdapter
    private lateinit var hoursArray: TypedArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val repository = MagicLineRepositoryImpl(MagicLineDB.getDatabase(this.applicationContext))
        val factory = MagicLineViewModelFactory(this.application, repository)
        mMagicLineViewModel = ViewModelProviders.of(this, factory).get(MagicLineViewModel::class.java)

        //Prepare the mapFAB
        bottomBarFloatingButton.setColorFilter(ContextCompat.getColor(this, R.color.selected_indicator_color))
        bottomBarFloatingButton.supportBackgroundTintList = ContextCompat.getColorStateList(this, R.color.white)
        getData()

        initBottomBar()

        if (savedInstanceState == null) {
            transitionWithModalAnimation(context = this, fragment = MagicLineFragment(),
                    useModalAnimation = false, addToBackStack = false, analyticsScreen = TrackingUtils.Screens.MagicLine)
            TrackingUtils(this).track(TrackingUtils.Screens.MagicLine)
            if (intent.hasExtra(FROM)) {
                navigateFromIntentExtra(intent.extras?.get(FROM) as Serializable?, false)
                isConnected()
            }
        }

        initNavigation()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    private fun initBottomBar() {
        bottomBarView.enableShiftingMode(false)
        bottomBarView.enableItemShiftingMode(false)
        bottomBarView.setTextSize(9.0f)
    }

    private fun initNavigation() {
        //Behaviour when clicked on a item different from map
        bottomBarView.setOnNavigationItemSelectedListener { item ->
            bottomBarFloatingButton.setColorFilter(ContextCompat.getColor(this, R.color.selected_indicator_color))
            bottomBarFloatingButton.supportBackgroundTintList = ContextCompat.getColorStateList(this, R.color.white)
            selectFragment(item)
            true
        }

        bottomBarFloatingButton.setOnClickListener {
            val mapButton = bottomBarView.menu.getItem(2)

            if (!mapButton.isChecked) {
                bottomBarFloatingButton.setColorFilter(ContextCompat.getColor(this, R.color.white))
                bottomBarFloatingButton.supportBackgroundTintList = ContextCompat.getColorStateList(this, R.color.colorPrimary)

                //We set clicked on the none item in order to disable the rest of the items
                //but the fragment that is shown is the map fragment
                mapButton.isChecked = true

                transitionWithModalAnimation(context = this, fragment = MapFragment(), useModalAnimation = false,
                        addToBackStack = false, analyticsScreen = TrackingUtils.Screens.Map)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        this.supportFragmentManager.popBackStack()
        return true
    }

    private fun selectFragment(item: MenuItem) {

        if (!item.isChecked) {
            // Those are fragments of the main view, so we don't need the back-stack
            clearBackStack()
            val newFragment: BaseFragment
            var analyticsScreen: TrackingUtils.Screens = TrackingUtils.Screens.MagicLine

            when (item.itemId) {
                R.id.magicline_menu_id -> {
                    newFragment = MagicLineFragment()
                    analyticsScreen = TrackingUtils.Screens.MagicLine
                }
                R.id.donations_menu_id -> {
                    newFragment = DonationsFragment()
                    analyticsScreen = TrackingUtils.Screens.Donations
                }
                R.id.info_menu_id -> {
                    newFragment = OptionsFragment()
                    analyticsScreen = TrackingUtils.Screens.Options
                }
                R.id.schedule_menu_id -> {
                    if (getFlavor() != VALENCIA) {
                        newFragment = ScheduleFragment.newInstance(scheduleModel)
                        analyticsScreen = TrackingUtils.Screens.Schedule
                        TrackingUtils(applicationContext).track(TrackingUtils.Screens.Schedule)
                    } else {
                        notAvailableDialog(R.string.notAvailableText, R.string.notAvailableBody)
                        //bottomBarView.getBottomNavigationItemView(0).setIconTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary))
                        bottomBarView.getBottomNavigationItemView(1).setIconTintList(ContextCompat.getColorStateList(this, R.color.moreinfo_background))
                        return
                    }
                }
                R.id.none -> return
                else -> newFragment = MagicLineFragment()
            }

            transitionWithModalAnimation(context = this, fragment = newFragment, useModalAnimation = false,
                    addToBackStack = false, analyticsScreen = analyticsScreen)
            currentFragment = newFragment
        }
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(Intent().apply { putExtra(FROM, DONATION) })
        val extra = intent?.getSerializableExtra(FROM)
        navigateFromIntentExtra(extra)
    }
    private fun navigateFromIntentExtra(extra: Serializable?, openDefault: Boolean = true) {

        when (extra) {
            LAST_NEWS -> { getNotificationNews() }
            DONATION -> {
                bottomBarView.menu.getItem(DONATIONS).isChecked = true
                this.transitionWithModalAnimation(context = this, fragment = DonationsFragment(), analyticsScreen = TrackingUtils.Screens.Donations)
            }
            EVENT -> {
                bottomBarView.menu.getItem(SCHEDULE).isChecked = true
                this.transitionWithModalAnimation(context = this, fragment = ScheduleFragment.newInstance(scheduleModel), analyticsScreen = TrackingUtils.Screens.Schedule)
            }
            else -> {
                bottomBarView.menu.getItem(HOME).isChecked = true
                if (openDefault) this.transitionWithModalAnimation(context = this, fragment = MagicLineFragment(), analyticsScreen = TrackingUtils.Screens.MagicLine)
            }
        }
    }

    private fun getNotificationNews(){
        myNewsAdapter = NewsAdapter(emptyList())
        mMagicLineViewModel.getPosts(getAPILang(context = this)).observe(this, androidx.lifecycle.Observer {
            myNewsAdapter.loadItems(toNewsModel(it))
            myNewsAdapter.notifyDataSetChanged()})
    }

    private fun toNewsModel(list: List<PostsItem>): List<NewsModel> {
        val news : MutableList<NewsModel> = mutableListOf()
        val onClickListener: (NewsModel) -> Unit = {}

            val detailModel = DetailModel(
                    title = list[INITIAL_POSITION].post.title.toString(),
                    subtitle = list[INITIAL_POSITION].post.teaser.toString(),
                    textBody = list[INITIAL_POSITION].post.text.toString(),
                    listToolbarImg = emptyList(),
                    listPostImg = list[INITIAL_POSITION].postImages,
                    hasToolbarImg = false,
                    link = list[INITIAL_POSITION].post.url.toString()
            )

            news.add(NewsModel(
                    detailModel = detailModel,
                    listener = onClickListener
            ))

        this.transitionWithModalAnimation(
                context = this,
                fragment = DetailFragment.newInstance(detailModel),
                analyticsScreen = TrackingUtils.Screens.NewsDetail
        )
        return news
    }

    private fun getData() {
        hoursArray = resources.obtainTypedArray(R.array.arrayScheduleHoursTimeStamp)
        val onClickListener: (DetailModel) -> Unit = { detailModel ->
            this.transitionWithModalAnimation(
                    context = this,
                    fragment = DetailFragment.newInstance(detailModel),
                    analyticsScreen = TrackingUtils.Screens.ScheduleDetail
            )
        }
        scheduleModel = getListeners(this, onClickListener = onClickListener)
    }



    private fun clearBackStack() {
        while (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    fun manageBottomBar(isModal : Boolean) {
        if (isModal) {
            bottomBarView.visibility = View.GONE
            bottomBarFloatingButton.hide()
        } else {
            bottomBarView.visibility = View.VISIBLE
            bottomBarFloatingButton.show()
        }
    }

    fun isConnected(): Boolean {
        val conMgr = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo

        if (netInfo == null || !netInfo.isConnected || !netInfo.isAvailable) {
           // Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show()
            notAvailableDialog(R.string.noWifiTitle, R.string.noWifiBody)

            return false
        }
        return true
    }
}
