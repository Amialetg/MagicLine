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
        //save data persistently
        val prefs : SharedPreferences = this.context.getSharedPreferences("Settings", Activity.MODE_PRIVATE )
        val editor = prefs.edit()


        if (data != null) {

            when (data["type"] ) {
                "ultimaNoticia" ->{
//                  intent.putExtra("From", "ultimaNoticia")

                    editor.putString("From", "ultimaNoticia")
                    editor.apply()
                }
                "ferDonacio" -> {

//                    intent.putExtra("From", "ferDonacio")
                    editor.putString("From", "ferDonacio")
                    editor.apply()

                }
                "detallsEsdeveniments" -> {
                    editor.putString("From", "detallsEsdeveniments")
                    editor.apply()

                }
                else -> { // Navega a la página principal
                    editor.putString("From", "main")
                    editor.apply()


                }
            }
        }
        //intent.setFlags = (Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or )

        this.context.startActivity(intent)


    }
}