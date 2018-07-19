package com.voluntariat.android.magicline.activities.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.voluntariat.android.magicline.R
import android.support.annotation.NonNull
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.MenuItem
import com.voluntariat.android.magicline.activities.main.fragments.*


class MainActivity: AppCompatActivity(){

    //Bottom Toolbar
    lateinit var bottomBarView: com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initWidgets()

        initBottomBar()

        initNavigation()
    }

    private fun initWidgets(){
        //BottomBar
        bottomBarView = findViewById(R.id.bottom_navigation)
    }

    private fun initBottomBar(){

        bottomBarView.enableShiftingMode(false)
        bottomBarView.enableItemShiftingMode(false)
        bottomBarView.setTextSize(9.0f)

    }

    private fun initNavigation(){

        //default item
        selectFragment(bottomBarView.menu.getItem(0))

        //Behaviour when clicked
        bottomBarView.setOnNavigationItemSelectedListener { item ->
            selectFragment(item) ;true
        }
    }
    private fun selectFragment(item:MenuItem){

        Log.d("Main Activity", "${item.itemId}  magic line id: ${R.id.magicline_menu_id}")

        var fragment:Fragment = MagicLineFragment()

        when(item.itemId) {
            R.id.magicline_menu_id -> {
                fragment = MagicLineFragment()
                Log.d("Main Activity", "magic line")
            }
            R.id.donations_menu_id -> {
                fragment = DonationsFragment()
                Log.d("Main Activity", "donations")
            }
            R.id.info_menu_id -> {
                fragment = InfoFragment()
                Log.d("Main Activity", "info")
            }
            R.id.schedule_menu_id-> fragment = ScheduleFragment()
        }


        var trans = this.supportFragmentManager.beginTransaction()
        trans.replace(R.id.frame_layout, fragment)
        trans.commit()


    }

}
