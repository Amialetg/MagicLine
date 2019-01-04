package com.obrasocialsjd.magicline.activities.main.general

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.obrasocialsjd.magicline.utils.getLanguagePreferences
import com.obrasocialsjd.magicline.utils.updateBaseContextLocale

abstract class BaseActivity : AppCompatActivity() {

    /**
     *  Forcing a language locale based on user preferences
     */
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base.updateBaseContextLocale(getLanguagePreferences(base)))
    }

    /**
     *  Infix function to start an activity without leaving the current one on the backstack
     */
    infix fun startAsRootActivity(activityClass: Class<out Activity>) {
        val intent = Intent(this, activityClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

}