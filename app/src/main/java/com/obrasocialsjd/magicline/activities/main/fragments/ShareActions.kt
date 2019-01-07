package com.obrasocialsjd.magicline.activities.main.fragments

import androidx.fragment.app.FragmentActivity
import com.obrasocialsjd.magicline.R

interface ShareActions {

    fun initRRSS(activity: FragmentActivity) {
        val fragment = ShareFragment()
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.shareContainer, fragment)
        transaction.commit()
    }

    fun removeRRSS(activity: FragmentActivity) {
        val fragment = ShareFragment()
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.remove(fragment)
        transaction.commit()
    }


}
