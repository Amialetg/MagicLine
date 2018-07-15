package com.voluntariat.android.magicline

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class TestToolbar : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_nav_layout)
        val bar = findViewById<com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx>(R.id.bottom_navigation)
        bar.enableShiftingMode(false)
        bar.enableItemShiftingMode(false)
        bar.setTextSize(9.0f)
    }

}
