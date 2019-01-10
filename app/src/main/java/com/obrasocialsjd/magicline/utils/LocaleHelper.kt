package com.obrasocialsjd.magicline.utils
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

fun getLanguagePreferences(context: Context) :String {
    val preferences = context.getSharedPreferences(PREF_SETTINGS, Activity.MODE_PRIVATE)

    return preferences.getString(PREF_LANGUAGE, "")
}

fun getCurrency() : String {
    //return Currency.getInstance(Locale.getDefault()).symbol
    return EURO
}

fun getAPILang(context: Context) : String {
    val preferencesLang = getLanguagePreferences(context)
    return when(preferencesLang) {
        SPANISH -> SPANISH_API
        else -> CATALAN_API
    }
}

fun getLocaleTagForString(locale: String) : String {
    return when (locale) {
        SPANISH_LOCALE -> SPANISH
        else -> ""
    }
}

fun updatePreferencesLanguage(context: Context, localeTag: String) {
    val preferences = context.getSharedPreferences(PREF_SETTINGS, Activity.MODE_PRIVATE)

    val editor: SharedPreferences.Editor = preferences.edit()

    editor.putString(PREF_LANGUAGE, localeTag)
    editor.apply()
}