package com.voluntariat.android.magicline.Utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.*

class Const {
    companion object {
        var languagePressed = 0

         fun loadLocale(context: Context) {
            var preferences: SharedPreferences = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
            var language:String = preferences.getString("My_Lang", "")
            Const.languagePressed = preferences.getInt("my_check", 0)
            setLocale(language, languagePressed, context)

        }
         fun setLocale(lang: String, isCheck: Int, context: Context) {
            var locale : Locale = Locale(lang)
            Locale.setDefault(locale)
            var configuration: Configuration = Configuration()
            configuration.locale = locale
            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
            //save the data shared preferences
            var editor : SharedPreferences.Editor = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE ).edit()
            editor.putString("My_Lang", lang)
            editor.putInt("my_check", isCheck)
            editor.apply()

        }
    }



}