package com.obrasocialsjd.magicline.activities.main.fragments

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import com.obrasocialsjd.magicline.R

interface FragmentActions {

    fun initRRSS(activity: FragmentActivity) {
        val fragment = ShareFragment()
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.commit()
    }

    fun callUriIntent(activity: FragmentActivity, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }
}
