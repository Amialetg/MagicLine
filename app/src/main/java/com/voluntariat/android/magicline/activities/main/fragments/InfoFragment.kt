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
import android.widget.TextView
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.Utils.Const
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.layout_checkboxs_info.*
import java.util.*


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

        if(Const.languagePressed == 1){
            checkbox_spanish_text.setCompoundDrawablesWithIntrinsicBounds( R.drawable.checkbox_pressed, 0, 0, 0);
            checkbox_catalan_text.setCompoundDrawablesWithIntrinsicBounds( R.drawable.checkbox_not_pressed, 0, 0, 0);

            isVersionLower(checkbox_spanish_text, R.style.MoreInfoLanguagePressed)
            isVersionLower(checkbox_catalan_text, R.style.MoreInfoLanguageNotPressed)
        }else{
            checkbox_catalan_text.setCompoundDrawablesWithIntrinsicBounds( R.drawable.checkbox_pressed, 0, 0, 0);
            checkbox_spanish_text.setCompoundDrawablesWithIntrinsicBounds( R.drawable.checkbox_not_pressed, 0, 0, 0);

            isVersionLower(checkbox_catalan_text, R.style.MoreInfoLanguagePressed)
            isVersionLower(checkbox_spanish_text, R.style.MoreInfoLanguageNotPressed)
        }

        checkbox_spanish_text.setOnClickListener{
            if(Const.languagePressed!=1){

                checkbox_spanish_text.setCompoundDrawablesWithIntrinsicBounds( R.drawable.checkbox_pressed, 0, 0, 0);
                checkbox_catalan_text.setCompoundDrawablesWithIntrinsicBounds( R.drawable.checkbox_not_pressed, 0, 0, 0);

                isVersionLower(checkbox_spanish_text, R.style.MoreInfoLanguagePressed)
                isVersionLower(checkbox_catalan_text, R.style.MoreInfoLanguageNotPressed)

                Const.languagePressed=1

               Const.setLocale("es", Const.languagePressed, context)
                refreshFragment()
            }
        }

        checkbox_catalan_text.setOnClickListener{
            if(Const.languagePressed!=2){
                checkbox_catalan_text.setCompoundDrawablesWithIntrinsicBounds( R.drawable.checkbox_pressed, 0, 0, 0);
                checkbox_spanish_text.setCompoundDrawablesWithIntrinsicBounds( R.drawable.checkbox_not_pressed, 0, 0, 0);

                isVersionLower(checkbox_catalan_text, R.style.MoreInfoLanguagePressed)
                isVersionLower(checkbox_spanish_text, R.style.MoreInfoLanguageNotPressed)

                Const.languagePressed=2

                Const.setLocale("ca", Const.languagePressed, context)
                refreshFragment()

            }
        }


    }

    fun refreshFragment() {
        val ft = fragmentManager.beginTransaction()
        ft.detach(this).attach(this).commit()
    }
    private fun isVersionLower(textView: TextView,resId :Int){

        if (Build.VERSION.SDK_INT < 23) {
            textView.setTextAppearance(context, resId)
        } else {
            textView.setTextAppearance(resId)
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