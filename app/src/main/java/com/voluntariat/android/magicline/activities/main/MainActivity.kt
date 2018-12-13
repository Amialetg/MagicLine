package com.voluntariat.android.magicline.activities.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.activities.main.fragments.DonationsFragment
import com.voluntariat.android.magicline.activities.main.fragments.InfoFragment
import com.voluntariat.android.magicline.activities.main.fragments.MagicLineFragment
import com.voluntariat.android.magicline.activities.main.fragments.MapFragment
import com.voluntariat.android.magicline.activities.main.fragments.ScheduleFragment
import com.voluntariat.android.magicline.data.MagicLineRepositoryImpl
import com.voluntariat.android.magicline.data.Result
import com.voluntariat.android.magicline.db.MagicLineDB


class MainActivity : BaseActivity() {

    //Bottom Toolbar
    private lateinit var bottomBarView: com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
    private lateinit var bottomBarBtn: FloatingActionButton

    //The app starts at the magic line fragment
    private var currentFragment: Fragment = MagicLineFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initWidgets()

        initBottomBar()

        if (savedInstanceState == null) {
            //First time we open the app
            val transaction = this.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, MagicLineFragment())
            transaction.commit()
        }
        initNavigation()

        /**
         * TODO remove. Example of how to call API
         */
        val repo = MagicLineRepositoryImpl(MagicLineDB.getDatabase(applicationContext))
        repo.authenticate(
                "apiml",
                "4p1ml2018"
        ) { result -> when (result) {
            is Result.Success -> Log.d("apiLogin","Success, token: ${result.data}")
            is Result.Failure -> Log.d("apiLogin","Failure: ${result.throwable.message}")
        } }
        repo.getPosts { result -> when (result) {
            is Result.Success -> {Log.d("apiLogin","Success, token: ${result.data}")

            }
            is Result.Failure -> Log.d("apiLogin","Failure: ${result.throwable.message}")
        } }
    }

    private fun initWidgets() {

        //BottomBar
        bottomBarView = findViewById(R.id.bottom_navigation)
        bottomBarBtn = findViewById(R.id.fab)
    }


    override fun onBackPressed() {
        val count = fragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            fragmentManager.popBackStack()
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
            bottomBarBtn.setColorFilter(Color.argb(255,74,74,74))

            selectFragment(item)
            true
        }

        //Behaviour when clicked on the map item
        bottomBarBtn.setOnClickListener {

            bottomBarBtn.setColorFilter(Color.argb(255,237,53,37))

            //We set clicked on the none item in order to disable the rest of the items
            //but the fragment that is shown is the map fragment
            bottomBarView.menu.getItem(2).isChecked = true

            val transaction = this.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, MapFragment())
            transaction.commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        this.supportFragmentManager.popBackStack()
        return true
    }

    private fun selectFragment(item: MenuItem) {

        val newFragment: Fragment

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
            R.id.schedule_menu_id -> newFragment = ScheduleFragment()
            R.id.none -> return
            else -> newFragment = MagicLineFragment()
        }

        val trans = this.supportFragmentManager.beginTransaction()
        trans.replace(R.id.frame_layout, newFragment)
//        trans.addToBackStack(newFragment.javaClass.canonicalName)
        trans.commit()

        currentFragment = newFragment



    }


}
