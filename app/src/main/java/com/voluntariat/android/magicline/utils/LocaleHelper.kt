package com.voluntariat.android.magicline.utils
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import java.util.*

fun Context.updateBaseContextLocale(language: String? = null): Context {
    if (!language.isNullOrEmpty()) {
        var lang = language!!.split("_")
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

    return preferences.getString("My_Lang", "")
}

fun getCurrency() : String {
    //return Currency.getInstance(Locale.getDefault()).symbol
    return "€"
}

fun getAPILang(context: Context) : String {
    val preferencesLang = getPreferencesLanguage(context)
    return when(preferencesLang) {
        "es_ES" -> "spa"
        "ca_ES" -> "cat"
        else -> "eng"
    }
}

fun getLocaleTagForString(locale: String) : String {
    return when (locale) {
        "es" -> "es_ES"
        "ca" -> "ca_ES"
        else -> "en_US"
    }
}

fun updatePreferencesLanguage(context: Context, localeTag: String) {
    val preferences = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE)

    val editor: SharedPreferences.Editor = preferences.edit()

    editor.putString("My_Lang", localeTag)
    editor.apply()
}