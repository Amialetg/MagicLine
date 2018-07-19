package com.voluntariat.android.magicline

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

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