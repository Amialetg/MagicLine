package com.obrasocialsjd.magicline.activities.main.general

import android.app.Application
import com.obrasocialsjd.magicline.utils.*
import com.onesignal.OneSignal
import java.util.*

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(ExampleNotificationOpenedHandler(this))
                .init()

        when (getFlavor()) {
            BARCELONA -> {
                OneSignal.sendTag(LOCATION, BARCELONA)
            }
            MALLORCA -> {
                OneSignal.sendTag(LOCATION, MALLORCA)
            }
            VALENCIA -> {
                OneSignal.sendTag(LOCATION, VALENCIA)
            }
        }

        val language = getLocaleTagForString(Locale.getDefault().language)
        updatePreferencesLanguage(this, language)
        updateBaseContextLocale(language)
    }
}