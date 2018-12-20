package com.voluntariat.android.magicline.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.onesignal.OSNotificationOpenResult
import com.onesignal.OneSignal
import com.onesignal.OSNotificationAction
import android.content.Intent
import android.content.SharedPreferences
import com.voluntariat.android.magicline.activities.main.MainActivity




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