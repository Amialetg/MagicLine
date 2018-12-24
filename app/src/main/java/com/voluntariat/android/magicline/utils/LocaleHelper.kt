package com.voluntariat.android.magicline.utils
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

fun Context.updateBaseContextLocale(language: String? = null): Context {
    if (language != null) {
        var lang = language.split("_")
        var locale = Locale(lang[0], lang[1])
        Locale.setDefault(locale)
        return updateResourcesLocale(this, locale)
    }
    return this
}

fun getLocaleString(conf: Configuration): String {
    return getLocale(conf).toString()
}
fun getLocale(conf: Configuration): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        conf.locales.get(0)
    } else {
        @Suppress("DEPRECATION")
        conf.locale
    }
}

private fun updateResourcesLocale(context: Context, locale: Locale): Context {
    val configuration = context.resources.configuration
    configuration.setLocale(locale)
    return context.createConfigurationContext(configuration)
}

fun getPreferencesLanguage(context: Context) :String {
    val preferences = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE)

    return preferences.getString("My_Lang", "es_ES")
}

fun getCurrency() : String {
    //return Currency.getInstance(Locale.getDefault()).symbol
    return "€"
}