package com.voluntariat.android.magicline.activities.main.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voluntariat.android.magicline.R
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.layout_checkboxs_info.*

class InfoFragment:Fragment(){

    private var languagePressed=1 // 1--> SPANISH 2-->CATALAN

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_info, container,  false)
    }

    override fun onStart() {
        super.onStart()

        initLanguageSettings()

        listener()
    }

    private fun initLanguageSettings(){
        spanish_relative.setOnClickListener{
            if(languagePressed!=1){
                checkbox_spanish.setImageResource(R.drawable.checkbox_pressed)
                checkbox_spanish_text.setTextAppearance(context, R.style.MoreInfoLanguagePressed)

                checkbox_catalan.setImageResource(R.drawable.checkbox_not_pressed)
                checkbox_catalan_text.setTextAppearance(context, R.style.MoreInfoLanguageNotPressed)

                languagePressed=1
            }
        }

        catalan_relative.setOnClickListener{
            if(languagePressed!=2){
                checkbox_catalan.setImageResource(R.drawable.checkbox_pressed)
                checkbox_catalan_text.setTextAppearance(context, R.style.MoreInfoLanguagePressed)

                checkbox_spanish.setImageResource(R.drawable.checkbox_not_pressed)
                checkbox_spanish_text.setTextAppearance(context, R.style.MoreInfoLanguageNotPressed)

                languagePressed=2
            }
        }
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