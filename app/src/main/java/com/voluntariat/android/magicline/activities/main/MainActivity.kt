package com.voluntariat.android.magicline.activities.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.voluntariat.android.magicline.R

class MainActivity: AppCompatActivity(){

    //Bottom Toolbar
    lateinit var bottomBarView: com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initWidgets()

        initBottomBar()
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

}