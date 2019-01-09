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
import androidx.core.content.ContextCompat.*
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.R.string.fb_pkg
import kotlinx.android.synthetic.main.fragment_invite_friends.*
import kotlinx.android.synthetic.main.fragment_invite_friends.view.*
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

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        inviteFriendsView.crossBtn.setBackgroundResource(R.drawable.ic_black_cross)
        inviteFriendsView.crossBtn.setOnClickListener { this.requireActivity().onBackPressed() }
        inviteFriendsView.textViewHeader.setText(R.string.inviteFriends)
    }

    private fun initRRSSListeners(){

        emailTextView.setOnClickListener{
            callIntent(getString(R.string.email_pkg))
        }
        fbTextView.setOnClickListener{
            callIntent(getString(R.string.fb_pkg))
        }
        msnTextView.setOnClickListener{
            callIntent(getString(R.string.msn_pkg))
        }
        telegramTextView.setOnClickListener{
            callIntent(getString(R.string.tel_pkg))
        }
        whatsTextView.setOnClickListener{
            callIntent(getString(R.string.whats_pkg))
        }

    }

    private fun callIntent(pkg: String) {

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