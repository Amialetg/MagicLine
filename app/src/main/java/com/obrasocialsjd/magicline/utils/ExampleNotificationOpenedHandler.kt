package com.obrasocialsjd.magicline.utils

import android.content.Context
import android.content.Intent
import com.obrasocialsjd.magicline.activities.main.general.MainActivity
import com.onesignal.OSNotificationOpenResult
import com.onesignal.OneSignal


internal class ExampleNotificationOpenedHandler(context: Context) : OneSignal.NotificationOpenedHandler {

    val context = context

    // This fires when a notification is opened by tapping on it.
    override fun notificationOpened(result: OSNotificationOpenResult) {
        val data = result.notification.payload.additionalData
        val intent = Intent(context, MainActivity::class.java)

        if (data != null) {

            when (data["type"] ) {
                "ultimaNoticia" ->{
                  intent.putExtra("From", "ultimaNoticia")
                }
                "ferDonacio" -> {
                  intent.putExtra("From", "ferDonacio")
                }
                "detallsEsdeveniments" -> {
                    intent.putExtra("From", "detallsEsdeveniments")
                }
            }
        }
        this.context.startActivity(intent)
    }
}