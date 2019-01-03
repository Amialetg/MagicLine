package com.obrasocialsjd.magicline.activities.main.general

import android.app.Application
import com.obrasocialsjd.magicline.utils.ExampleNotificationOpenedHandler
import com.obrasocialsjd.magicline.utils.getLocaleTagForString
import com.obrasocialsjd.magicline.utils.updateBaseContextLocale
import com.obrasocialsjd.magicline.utils.updatePreferencesLanguage
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

        val language = getLocaleTagForString(Locale.getDefault().language)
        updatePreferencesLanguage(this, language)
        updateBaseContextLocale(language)
    }
}