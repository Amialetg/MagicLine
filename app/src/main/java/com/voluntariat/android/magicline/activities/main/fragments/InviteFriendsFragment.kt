package com.voluntariat.android.magicline.activities.main.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.voluntariat.android.magicline.R
import android.widget.Toast
import android.content.pm.PackageManager
import android.content.pm.PackageInfo



class InviteFriendsFragment: Fragment(){

    lateinit var textView_email: TextView
    lateinit var textView_facebook: TextView
    lateinit var textView_messenger: TextView
    lateinit var textView_telegram: TextView
    lateinit var textView_whatsapp: TextView

    override fun onStart() {
        textView_email = view!!.findViewById(R.id.textView_email)
//        textView_facebook = view!!.findViewById(R.id.textView_whats)
        super.onStart()
        initRRSSListeners()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invite_friends, container, false)


    }

    private fun initRRSSListeners(){

        textView_email.setOnClickListener{
            callIntent()
        }

//        textView_facebook.setOnClickListener{
           // callIntent(urlGoogle)

           // onClickWhatsApp(view: View)



//        }

//        textView_messenger.setOnClickListener{
//           // callIntent(urlTwitter)
//        }
//
//        textView_telegram.setOnClickListener{
//          //  callIntent(urlGoogle)
//        }
//
//        textView_whatsapp.setOnClickListener{
//         //   callIntent(urlTwitter)
//        }

    }

    private fun callIntent(){

        try {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, "Magic Line")
            var strShareMessage = "\nLet me recommend you this application\n\n"
            strShareMessage = strShareMessage + "https://play.google.com/store/apps/details?id=" + "com.basetis.ecolocalapp" //Substituir por Magic Line
            val screenshotUri = Uri.parse("android.resource://res/drawable/pantallasplash")
            i.type = "image/png"
            //i.setPackage("mail.google.com")
            i.putExtra(Intent.EXTRA_STREAM, screenshotUri)
            i.putExtra(Intent.EXTRA_TEXT, strShareMessage)
            startActivity(Intent.createChooser(i, "Share via"))
        } catch (e: Exception) {
            //e.toString();
        }


    }

//    fun onClickWhatsApp(view: View) {

//        val pm = "com.basetis.ecolocalapp"
//        try {
//
//            val waIntent = Intent(Intent.ACTION_SEND)
//            waIntent.type = "text/plain"
//            val text = "YOUR TEXT HERE"
//
//            val info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)
//            //Check if package exists or not. If not then code
//            //in catch block will be called
//            waIntent.setPackage("com.whatsapp")
//
//            waIntent.putExtra(Intent.EXTRA_TEXT, text)
//            startActivity(Intent.createChooser(waIntent, "Share with"))
//
//        } catch (e: PackageManager.NameNotFoundException) {
//            Toast.makeText(this.context, "WhatsApp not Installed", Toast.LENGTH_SHORT).show()
//        }

//    }
}