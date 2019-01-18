package com.obrasocialsjd.magicline.utils

import android.app.Activity
import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.obrasocialsjd.magicline.BuildConfig

class TrackingUtil(val context: Context) {
    enum class Screens {
        MagicLine,
        NewsDetail,
        Detail,
        MagicLineInfo,
        Donations,
        AboutApp,
        Options,
        Schedule,
        Map,
        InviteFriends
    }

    fun track(screen: Screens) {
        if (!BuildConfig.DEBUG) {
            FirebaseAnalytics.getInstance(context.applicationContext)
                    .setCurrentScreen(Activity(), screen.name, null)
        }
    }
}