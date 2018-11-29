package com.voluntariat.android.magicline.activities.main.fragments

import android.content.Intent
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
import kotlinx.android.synthetic.main.fragment_invite_friends.*


class InviteFriendsFragment: Fragment(){

    override fun onStart() {
        super.onStart()
        initRRSSListeners()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_invite_friends, container, false)

    }

    private fun initRRSSListeners(){

        val facebookPkg = getString(R.string.facebook_packg)
        val messengerPkg = getString(R.string.messenger_packg)
        val emailPkg = getString(R.string.email_packg)
        val telegramPkg = getString(R.string.telegram_packg)
        val whatsappPkg = getString(R.string.whatsapp_packg)

        emailTextView.setOnClickListener{
            callIntent(emailPkg)
        }
        facebookTextView.setOnClickListener{
            callIntent(facebookPkg)
        }
        messengerTextView.setOnClickListener{
            callIntent(messengerPkg)
        }
        telegramTextView.setOnClickListener{
            callIntent(telegramPkg)
        }
        whatsappTextView.setOnClickListener{
            callIntent(whatsappPkg)
        }

    }

    fun callIntent(pkg: String) {

        //val pm = context.packageManager//getPackageManager()
        try {
            val waIntent = Intent(Intent.ACTION_SEND)
            waIntent.type = "text/plain"
            waIntent.putExtra(Intent.EXTRA_SUBJECT, "Magic Line")
            var text = "\n" + " Let me recommend you this application\n" + "\n"
            text = text + "https://play.google.com/store/apps/details?id=" + "com.basetis.ecolocalapp" //Substituir por Magic Line
            //val screenshotUri = Uri.parse("android.resource://res/drawable/pantallasplash")
            //i.type = "image/png"
            //i.putExtra(Intent.EXTRA_STREAM, screenshotUri)
           // val info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)
            // Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage(pkg)
            waIntent.putExtra(Intent.EXTRA_TEXT, text)
            startActivity(Intent.createChooser(waIntent, "Share with"))

        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(this.context, "App not Installed", Toast.LENGTH_SHORT).show()
        }
    }
}