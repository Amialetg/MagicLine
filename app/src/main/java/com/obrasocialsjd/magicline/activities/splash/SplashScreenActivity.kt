package com.obrasocialsjd.magicline.activities.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.obrasocialsjd.magicline.BuildConfig
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.activities.main.general.MainActivity
import com.obrasocialsjd.magicline.data.MagicLineRepositoryImpl
import com.obrasocialsjd.magicline.data.Result
import com.obrasocialsjd.magicline.db.MagicLineDB

/**
 * Created by hector on 26/06/18.
 */
class SplashScreenActivity : Activity() {
    private var handler: Handler? = null
    private val SPLASH_DELAY: Long = 200

    private val mRunnable: Runnable = Runnable {
        if(!isFinishing){
            authenticate()
        }
    }

    private fun authenticate() {
        val repo = MagicLineRepositoryImpl(MagicLineDB.getDatabase(applicationContext))
        repo.authenticate(
                BuildConfig.MLAPIUser,
                BuildConfig.MLAPIPassword
        ) { result -> when (result) {
            is Result.Success -> {
                authenticationSuccess()
            }
        } }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        handler = Handler()

        handler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    public override fun onDestroy() {
        super.onDestroy()

        if(handler != null){
            handler!!.removeCallbacks(mRunnable)
        }
    }

    private fun authenticationSuccess() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}