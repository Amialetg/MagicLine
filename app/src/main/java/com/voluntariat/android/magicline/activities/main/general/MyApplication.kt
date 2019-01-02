package com.voluntariat.android.magicline.activities.main.general

import android.app.Application
import com.onesignal.OneSignal
import com.voluntariat.android.magicline.utils.ExampleNotificationOpenedHandler
import com.voluntariat.android.magicline.utils.getLocaleTagForString
import com.voluntariat.android.magicline.utils.updateBaseContextLocale
import com.voluntariat.android.magicline.utils.updatePreferencesLanguage
import java.util.*

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(ExampleNotificationOpenedHandler(this))
                .init()

        val language = getLocaleTagForString(Locale.getDefault().language)
        updatePreferencesLanguage(this, language)
        updateBaseContextLocale(language)
    }
}