package com.voluntariat.android.magicline.activities.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.onesignal.OneSignal
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.activities.main.MainActivity

/**
 * Created by hector on 26/06/18.
 */
class SplashScreenActivity : Activity(){
    private var handler: Handler? = null
    private val SPLASH_DELAY: Long = 200

    internal val mRunnable: Runnable = Runnable {
        if(!isFinishing){

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init()

        setContentView(R.layout.activity_splash_screen)

        handler = Handler()

        handler!!.postDelayed(mRunnable,SPLASH_DELAY)
    }

    public override fun onDestroy() {

        super.onDestroy()

        if(handler != null){
            handler!!.removeCallbacks(mRunnable)
        }
    }
}