package com.voluntariat.android.magicline.activities.main.fragments

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import com.voluntariat.android.magicline.R
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.layout_checkboxs_info.*


class InfoFragment:Fragment(){

     // 1--> SPANISH 2-->CATALAN

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_info, container,  false)
    }


    override fun onStart() {

        super.onStart()

        initLanguageSettings()

        listener()
    }

    private fun initLanguageSettings(){
        val prefs : SharedPreferences = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE )

        if(prefs.getString("My_Lang", "") == "ca"){
            checkbox_catalan_text.isChecked = true
        }else{
            checkbox_spanish_text.isChecked = true
        }


        checkbox_spanish_text.setOnCheckedChangeListener { checkbox_spanish_text, isChecked ->

            if(checkbox_spanish_text.isChecked){

                if (checkbox_catalan_text.isChecked){
                    checkbox_catalan_text.isChecked = false
                }

                val editor = prefs.edit()
                editor.putString("My_Lang", "es")
                editor.apply()
                refresh()
            }
        }

        checkbox_catalan_text.setOnCheckedChangeListener { checkbox_catalan_text, isChecked ->

            if(checkbox_catalan_text.isChecked){
                if (checkbox_spanish_text.isChecked){
                    checkbox_spanish_text.isChecked = false

                }
                val editor = prefs.edit()
                editor.putString("My_Lang", "ca")
                editor.apply()
                refresh()
            }
        }
    }

    fun refresh() {
        activity.recreate()
    }

    private fun listener(){
        val urlMagicLine= getString(R.string.magicLineWeb)

        moreInfoFriendsTextView.setOnClickListener{
            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, InviteFriendsFragment())
            transaction.addToBackStack(InviteFriendsFragment().javaClass.canonicalName)
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