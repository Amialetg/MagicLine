package com.voluntariat.android.magicline.activities.main.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.voluntariat.android.magicline.R

class InfoFragment:Fragment(){


    //Language widgets
    lateinit var spanishRelativeLayout: RelativeLayout
    lateinit var catalanRelativeLayout: RelativeLayout
    lateinit var checkBoxCatalan: ImageView
    lateinit var checkBoxSpanish: ImageView
    lateinit var checkBoxCatalanText: TextView
    lateinit var checkBoxSpanishText: TextView
    private var languagePressed=1 // 1--> SPANISH 2-->CATALAN
    lateinit var inviteFriendsListener: TextView


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_info, container,  false)
    }

    override fun onStart() {
        super.onStart()

        initWidgets()

        initLanguageSettings()

        inviteFriendsListener()
    }

    private fun initWidgets(){
        spanishRelativeLayout = view!!.findViewById(R.id.spanish_relative)
        catalanRelativeLayout = view!!.findViewById(R.id.catalan_relative)
        checkBoxCatalan = view!!.findViewById(R.id.checkbox_catalan)
        checkBoxSpanish = view!!.findViewById(R.id.checkbox_spanish)
        checkBoxCatalanText = view!!.findViewById(R.id.checkbox_catalan_text)
        checkBoxSpanishText = view!!.findViewById(R.id.checkbox_spanish_text)
        inviteFriendsListener = view!!.findViewById(R.id.textView_moreInfo_friends)


    }

    private fun initLanguageSettings(){
        spanishRelativeLayout.setOnClickListener{
            if(languagePressed!=1){
                checkBoxSpanish.setImageResource(R.drawable.checkbox_pressed)
                checkBoxSpanishText.setTextAppearance(context, R.style.MoreInfoLanguagePressed)

                checkBoxCatalan.setImageResource(R.drawable.checkbox_not_pressed)
                checkBoxCatalanText.setTextAppearance(context, R.style.MoreInfoLanguageNotPressed)

                languagePressed=1
            }
        }

        catalanRelativeLayout.setOnClickListener{
            if(languagePressed!=2){
                checkBoxCatalan.setImageResource(R.drawable.checkbox_pressed)
                checkBoxCatalanText.setTextAppearance(context, R.style.MoreInfoLanguagePressed)

                checkBoxSpanish.setImageResource(R.drawable.checkbox_not_pressed)
                checkBoxSpanishText.setTextAppearance(context, R.style.MoreInfoLanguageNotPressed)

                languagePressed=2
            }
        }
    }

    private fun inviteFriendsListener(){
        inviteFriendsListener.setOnClickListener{

            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, InviteFriendsFragment())
            transaction.commit()

        }
    }


}