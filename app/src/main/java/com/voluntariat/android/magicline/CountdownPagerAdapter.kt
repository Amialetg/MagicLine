package com.voluntariat.android.magicline

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log

class CountdownPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    private val numItems: Int = 2

    override fun getCount(): Int {
        return numItems
    }

    override fun getItem(position: Int): Fragment {
        Log.d("PagerView", "$position")
        return when(position){
            0->CountdownFragment()
            1-> RecaudatsFragment()
            else->CountdownFragment()
        }
    }



}