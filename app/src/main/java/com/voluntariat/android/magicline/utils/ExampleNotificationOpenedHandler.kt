package com.voluntariat.android.magicline.utils

import android.app.FragmentController
import android.app.FragmentManager
import android.content.Context
import android.util.Log
import com.onesignal.OSNotificationOpenResult
import com.onesignal.OneSignal
import com.voluntariat.android.magicline.activities.splash.SplashScreenActivity
import com.onesignal.OSNotificationAction
import android.content.Intent
import android.net.Uri
import android.support.v4.app.FragmentTransaction
import android.widget.Toast
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.activities.main.fragments.InviteFriendsFragment
import android.support.v4.content.ContextCompat.startActivity
import com.voluntariat.android.magicline.activities.main.MainActivity
import android.support.v4.app.FragmentActivity




internal class ExampleNotificationOpenedHandler(context: Context) : OneSignal.NotificationOpenedHandler {

    val context = context

    // This fires when a notification is opened by tapping on it.
    override fun notificationOpened(result: OSNotificationOpenResult) {
        val actionType = result.action.type
        val data = result.notification.payload.additionalData
        val customKey: String?
        val intent = Intent(context, MainActivity::class.java)


        if (data != null) {

            when (data["type"] ) {
                "ultimaNoticia" ->{
                    intent.putExtra("From", "ultimaNoticia")
                    this.context.startActivity(intent)
                }
                "ferDonacio" -> {
                    intent.putExtra("From", "ferDonacio")
                    this.context.startActivity(intent)

                }
                "detallsEsdeveniments" -> {
                    intent.putExtra("From", "detallsEsdeveniments")
                    this.context.startActivity(intent)

                }
                else -> { // Navega a la página principal

                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }
            customKey = data.optString("customkey", null)
            if (customKey != null)
                Log.i("OneSignalExample", "customkey set with value: $customKey")
        }

        if (actionType == OSNotificationAction.ActionType.ActionTaken)
            Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID)

    }



}