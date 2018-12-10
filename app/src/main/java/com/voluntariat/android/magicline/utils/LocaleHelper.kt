package com.voluntariat.android.magicline.utils
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

fun Context.updateBaseContextLocale(language: String? = null): Context {
    if (language != null) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        return updateResourcesLocale(this, locale)
    }
    return this
}

 fun getLocale(conf: Configuration): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        conf.locales.get(0).toString()
    } else {
        @Suppress("DEPRECATION")
        conf.locale.toString()
    }
}

private fun updateResourcesLocale(context: Context, locale: Locale): Context {
    val configuration = context.resources.configuration
    configuration.setLocale(locale)
    return context.createConfigurationContext(configuration)
}

fun getPreferencesLanguage(context: Context) :String{
    val preferences = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE)

    return preferences.getString("My_Lang", "")

}