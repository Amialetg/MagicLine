package com.voluntariat.android.magicline.activities.main.general

import android.app.Application
import com.onesignal.OneSignal
import com.voluntariat.android.magicline.activities.main.fragments.MagicLineFragment
import com.voluntariat.android.magicline.utils.ExampleNotificationOpenedHandler

class MyApplication : Application() {

    override fun onCreate() {

        super.onCreate()
        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(ExampleNotificationOpenedHandler(this))
                .init()





    }
}