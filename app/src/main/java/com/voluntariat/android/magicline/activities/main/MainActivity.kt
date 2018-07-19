package com.voluntariat.android.magicline.activities.main

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.voluntariat.android.magicline.R
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.MenuItem
import com.voluntariat.android.magicline.activities.main.fragments.*


class MainActivity : AppCompatActivity() {

    //Bottom Toolbar
    lateinit var bottomBarView: com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
    lateinit var bottomBarBtn: FloatingActionButton

    //The app starts at the magic line fragment
    private var currentFragment: Fragment = MagicLineFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initWidgets()

        initBottomBar()

        initNavigation()
    }

    private fun initWidgets() {
        //BottomBar
        bottomBarView = findViewById(R.id.bottom_navigation)
        bottomBarBtn = findViewById(R.id.fab)
    }

    private fun initBottomBar() {

        bottomBarView.enableShiftingMode(false)
        bottomBarView.enableItemShiftingMode(false)
        bottomBarView.setTextSize(9.0f)

    }

    private fun initNavigation() {

        //First time we open the app
        var trans = this.supportFragmentManager.beginTransaction()
        trans.replace(R.id.frame_layout, MagicLineFragment())
        trans.commit()

        //Behaviour when clicked
        bottomBarView.setOnNavigationItemSelectedListener { item ->
            bottomBarBtn.setColorFilter(Color.argb(255,74,74,74))
            item.setCheckable(true)
            selectFragment(item)
            true
        }
        bottomBarBtn.setOnClickListener {

            if (currentFragment != MapFragment()) {
                bottomBarBtn.setColorFilter(Color.argb(255,237,53,37))
                // unchecks the buttons of nav bar when user is in map
                for (i in 0 until bottomBarView.menu.size()) {
                    var m: MenuItem = bottomBarView.menu.getItem(i)
                    m.setChecked(false)
                    m.setCheckable(false)

                }

                var trans = this.supportFragmentManager.beginTransaction()
                trans.replace(R.id.frame_layout, MapFragment())
                trans.commit()
            }

        }


    }

    private fun selectFragment(item: MenuItem) {

        var newFragment: Fragment

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

        if (newFragment::class != currentFragment::class) {
            var trans = this.supportFragmentManager.beginTransaction()
            trans.replace(R.id.frame_layout, newFragment)
            trans.commit()

            currentFragment = newFragment
        }


    }

}
