package com.voluntariat.android.magicline

import android.app.Activity
import android.content.Intent
import android.net.wifi.hotspot2.pps.HomeSp
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by hector on 26/06/18.
 */
class SplashScreen : Activity(){
    private var handler: Handler? = null
    private val SPLASH_DELAY: Long = 3000

    internal val mRunnable: Runnable = Runnable {
        if(!isFinishing){

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

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