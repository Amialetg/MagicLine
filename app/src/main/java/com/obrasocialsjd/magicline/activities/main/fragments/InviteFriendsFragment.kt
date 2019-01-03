package com.obrasocialsjd.magicline.activities.main.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.obrasocialsjd.magicline.R
import kotlinx.android.synthetic.main.fragment_invite_friends.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.view.*


class InviteFriendsFragment: BaseFragment() {
    private lateinit var inviteFriendsView: View

    override fun onStart() {
        super.onStart()
        initToolbar()
        initRRSSListeners()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        inviteFriendsView = inflater.inflate(R.layout.fragment_invite_friends, container, false)
        return inviteFriendsView

    }

    private fun initToolbar(){
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        inviteFriendsView.topToolbar.setNavigationIcon(R.drawable.ic_black_cross)
        inviteFriendsView.topToolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this.requireContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_ATOP)
        inviteFriendsView.topToolbar.title = ""
        inviteFriendsView.topToolbar.background = ContextCompat.getDrawable(requireContext(), R.drawable.invite_friends_bg)
        inviteFriendsView.topToolbar.setNavigationOnClickListener { this.requireActivity().onBackPressed() }
        }

    private fun initRRSSListeners(){

        val fbPkg = getString(R.string.fb_pkg)
        val messengerPkg = getString(R.string.msn_pkg)
        val emailPkg = getString(R.string.email_pkg)
        val telegramPkg = getString(R.string.tel_pkg)
        val whatsappPkg = getString(R.string.whats_pkg)

        emailTextView.setOnClickListener{
            callIntent(emailPkg)
        }
        facebookTextView.setOnClickListener{
            callIntent(fbPkg)
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

    private fun callIntent(pkg: String) {

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