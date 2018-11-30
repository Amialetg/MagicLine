package com.voluntariat.android.magicline.activities.main.fragments

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voluntariat.android.magicline.R
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.layout_checkboxs_info.*
import java.util.*


class InfoFragment:Fragment(){

    private var languagePressed = 0 // 1--> SPANISH 2-->CATALAN

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_info, container,  false)
    }


    override fun onStart() {

        super.onStart()

        initLanguageSettings()

        listener()
    }

    private fun initLanguageSettings(){
        loadLocale()
        spanish_relative.setOnClickListener{
            if(languagePressed!=1){
                checkbox_spanish.setImageResource(R.drawable.checkbox_pressed)
                isVersionLower(R.style.MoreInfoLanguagePressed)
                checkbox_catalan.setImageResource(R.drawable.checkbox_not_pressed)
                isVersionLower(R.style.MoreInfoLanguageNotPressed)

                languagePressed=1
                
                setLocale("es")
//               refreshFragment()

            }
        }

        catalan_relative.setOnClickListener{
            if(languagePressed!=2){
                checkbox_catalan.setImageResource(R.drawable.checkbox_pressed)
                isVersionLower(R.style.MoreInfoLanguagePressed)
                checkbox_spanish.setImageResource(R.drawable.checkbox_not_pressed)
                isVersionLower(R.style.MoreInfoLanguageNotPressed)

                languagePressed=2

                setLocale("ca")
               // refreshFragment()
            }
        }
    }

    fun refreshFragment() {
        val ft = fragmentManager.beginTransaction()
        ft.detach(this).attach(this).commit()
    }
    private fun isVersionLower(resId :Int){

        if (Build.VERSION.SDK_INT < 23) {
            checkbox_spanish_text.setTextAppearance(context, resId)
        } else {
            checkbox_spanish_text.setTextAppearance(resId)
        }
    }

    private fun setLocale(lang : String) {
       var locale : Locale = Locale(lang)
        Locale.setDefault(locale)
        var configuration: Configuration = Configuration()
        configuration.locale = locale
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        //save the data shared preferences
        var editor : SharedPreferences.Editor = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE ).edit()
        editor.putString("My_Lang", lang)
        editor.apply()

    }

    public fun loadLocale() {
        var preferences: SharedPreferences = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        var language:String = preferences.getString("My_Lang", "")
        setLocale(language)

    }

    private fun listener(){
        val urlMagicLine= getString(R.string.magicLineWeb)

        moreInfoFriendsTextView.setOnClickListener{
            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, InviteFriendsFragment())
            transaction.commit()
        }
        webMagicLineTextView.setOnClickListener{
            callIntent(urlMagicLine)
        }

        basetisInfoTextView.setOnClickListener{
            //Navegar al fragment de BaseTIS
            print("Basetis Information")
        }
    }

    private fun callIntent(url : String){

        var intent : Intent
        intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)

    }
}