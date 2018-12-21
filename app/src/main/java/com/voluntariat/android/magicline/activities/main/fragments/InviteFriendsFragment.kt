package com.voluntariat.android.magicline.activities.main.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.R.id.*
import kotlinx.android.synthetic.main.fragment_invite_friends.emailTextView
import kotlinx.android.synthetic.main.fragment_invite_friends.facebookTextView
import kotlinx.android.synthetic.main.fragment_invite_friends.messengerTextView
import kotlinx.android.synthetic.main.fragment_invite_friends.telegramTextView
import kotlinx.android.synthetic.main.fragment_invite_friends.whatsappTextView
import kotlinx.android.synthetic.main.toolbar_appbar_top.view.*


class InviteFriendsFragment: BaseFragment() {

    private lateinit var inviteLayoutView: View

    override fun onStart() {
        super.onStart()
        initRRSSListeners()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        inviteLayoutView = inflater.inflate(R.layout.fragment_invite_friends, container, false)
        return inviteLayoutView


    }

    override fun onResume() {
        super.onResume()
        initToolbar()
    }

    private fun initToolbar(){
        inviteLayoutView.topToolbar.background = ContextCompat.getDrawable(this.requireContext(), R.drawable.invite_friends)
        inviteLayoutView.topToolbar.setTitleTextColor(ContextCompat.getColor(this.requireContext(), R.color.white))
        inviteLayoutView.topToolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this.requireContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_ATOP)
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
            text += "https://play.google.com/store/apps/details?id=com.obrasocialsjd.magicline&hl=cat"
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

    companion object {
        fun newInstance(): BaseFragment {
            return InviteFriendsFragment()
        }
    }
}