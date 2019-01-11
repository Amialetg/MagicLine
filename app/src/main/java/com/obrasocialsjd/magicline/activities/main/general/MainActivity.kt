package com.obrasocialsjd.magicline.activities.main.general

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
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
import java.util.ArrayList

class MainActivity : BaseActivity() {

    private lateinit var currentFragment: Fragment
    private var scheduleModel: Array<ScheduleGeneralModel> = arrayOf()
    private lateinit var mMagicLineViewModel: MagicLineViewModel
    private lateinit var myNewsAdapter: NewsAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val repository = MagicLineRepositoryImpl(MagicLineDB.getDatabase(this.applicationContext))
        val factory = MagicLineViewModelFactory(this.application, repository)
        mMagicLineViewModel = ViewModelProviders.of(this, factory).get(MagicLineViewModel::class.java)


        //Prepare the mapFAB
        bottomBarFloatingButton.setColorFilter(ContextCompat.getColor(this, R.color.selected_indicator_color))
        bottomBarFloatingButton.supportBackgroundTintList = ContextCompat.getColorStateList(this, R.color.white)

        getData()

        initBottomBar()

        if (savedInstanceState == null) {
            transitionWithModalAnimation(fragment = MagicLineFragment(),
                    useModalAnimation = false, addToBackStack = false)
            if (intent.hasExtra("From")) {
                navigateFromIntentExtra(intent.extras?.get("From") as Serializable?, false)
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

    fun initNavigation() {
        //Behaviour when clicked on a item different from map
        bottomBarView.setOnNavigationItemSelectedListener { item ->
            bottomBarFloatingButton.setColorFilter(ContextCompat.getColor(this, R.color.selected_indicator_color))
            bottomBarFloatingButton.supportBackgroundTintList = ContextCompat.getColorStateList(this, R.color.white)

            selectFragment(item)
            true
        }

        bottomBarFloatingButton.setOnClickListener {
            bottomBarFloatingButton.setColorFilter(ContextCompat.getColor(this, R.color.white))
            bottomBarFloatingButton.supportBackgroundTintList = ContextCompat.getColorStateList(this, R.color.colorPrimary)

            //We set clicked on the none item in order to disable the rest of the items
            //but the fragment that is shown is the map fragment
            bottomBarView.menu.getItem(2).isChecked = true

            transitionWithModalAnimation(fragment = MapFragment(), useModalAnimation = false,
                    addToBackStack = false)
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
            when (item.itemId) {
                R.id.magicline_menu_id -> {
                    newFragment = MagicLineFragment()
                    Log.d("Main Activity", "magic line")
                }
                R.id.donations_menu_id -> {
                    newFragment = DonationsFragment()
                    Log.d("Main Activity", "donations")
                }
                R.id.info_menu_id -> {
                    newFragment = InfoFragment()
                    Log.d("Main Activity", "info")
                }
                R.id.schedule_menu_id -> newFragment = ScheduleFragment.newInstance(scheduleModel)
                R.id.none -> return
                else -> newFragment = MagicLineFragment()
            }

            transitionWithModalAnimation(fragment = newFragment, useModalAnimation = false,
                    addToBackStack = false)
            currentFragment = newFragment
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(Intent().apply { putExtra("From", "ferDonacio") })
        val extra = intent?.getSerializableExtra("From")
        navigateFromIntentExtra(extra)
    }
    private fun navigateFromIntentExtra(extra: Serializable?, openDefault: Boolean = true) {





        when (extra) {
            "ultimaNoticia" -> { getNotificationNews() }
            "ferDonacio" -> {
                bottomBarView.menu.getItem(DONATIONS).isChecked = true
                this.transitionWithModalAnimation(DonationsFragment())
            }
            "detallsEsdeveniments" -> {
                bottomBarView.menu.getItem(SCHEDULE).isChecked = true

                this.transitionWithModalAnimation(ScheduleFragment.newInstance(scheduleModel))
            } //Especificar qué Fragment
            else -> {
                bottomBarView.menu.getItem(HOME).isChecked = true
                if (openDefault) this.transitionWithModalAnimation(MagicLineFragment())
            }
        }
    }

    private fun getNotificationNews(){
        myNewsAdapter = NewsAdapter(ArrayList())

        mMagicLineViewModel.getPosts(getAPILang(context = applicationContext)).observe(this, androidx.lifecycle.Observer {
            myNewsAdapter.loadItems(toNewsModel(it))
            myNewsAdapter.notifyDataSetChanged()})
    }

    private fun toNewsModel(list: List<PostsItem>): List<NewsModel> {
        val news : MutableList<NewsModel> = mutableListOf()
        val onClickListener: (NewsModel) -> Unit = {}

            val detailModel = DetailModel(
                    title = list[0].post.title.toString(),
                    subtitle = list[0].post.teaser.toString(),
                    textBody = list[0].post.text.toString(),
                    listToolbarImg = emptyList(),
                    listPostImg = list[0].postImages,
                    hasToolbarImg = false,
                    link = list[0].post.url.toString()
            )

            news.add(NewsModel(
                    detailModel = detailModel,
                    listener = onClickListener
            ))
        this.transitionWithModalAnimation(DetailFragment.newInstance(detailModel))

        return news
    }

    private fun getData() {
        scheduleModel = arrayOf(
                ScheduleTextModel("9:30", "Salida"),
                ScheduleCardModel("10:30", "Picnik", "Equipaments culturals obren les portes", getString(R.string.lorem_ipsum),
                        detailModel = DetailModel(
                                title = getString(R.string.essentials_title),
                                subtitle = getString(R.string.essentials_subtitle),
                                textBody = getString(R.string.essentials_body),
                                link = getString(R.string.essentials_viewOnWeb))),
                ScheduleTextModel("12:30", "Tornar a caminar"),
                ScheduleCardModel(
                        "13:30",
                        "Espectacle",
                        "Equipaments culturals obren les portes",
                        "In recent years people have realized the importance of proper diet and exercise, and recent surveys",
                        DetailModel(
                                title = getString(R.string.essentials_title),
                                subtitle = getString(R.string.essentials_subtitle),
                                textBody = getString(R.string.essentials_body),
                                link = getString(R.string.essentials_viewOnWeb))
                ),
                ScheduleTextModel("15:00", "Caminar una mica més"),
                ScheduleCardModel(
                        "16:30",
                        "Concerts",
                        "Equipaments culturals obren les portes",
                        "In recent years people",
                        detailModel = DetailModel(
                                title = getString(R.string.essentials_title),
                                subtitle = getString(R.string.essentials_subtitle),
                                textBody = getString(R.string.essentials_body),
                                link = getString(R.string.essentials_viewOnWeb))
                )
        )
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
}
