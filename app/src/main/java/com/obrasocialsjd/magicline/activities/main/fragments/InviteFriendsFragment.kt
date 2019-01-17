package com.obrasocialsjd.magicline.activities.main.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.utils.shareApp
import kotlinx.android.synthetic.main.fragment_invite_friends.*
import kotlinx.android.synthetic.main.fragment_invite_friends.view.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*


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

        try {

        emailTextView.setOnClickListener{
            openActivity(shareApp(getString(R.string.email_pkg)))
        }
        fbTextView.setOnClickListener{
            openActivity(shareApp(getString(R.string.fb_pkg)))
        }
        msnTextView.setOnClickListener{
            openActivity(shareApp(getString(R.string.msn_pkg)))
        }
        telegramTextView.setOnClickListener{
            openActivity(shareApp(getString(R.string.tel_pkg)))
        }
        whatsTextView.setOnClickListener{
            openActivity(shareApp(getString(R.string.whats_pkg)))
        }

        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(this.context, getString(R.string.appNotInstalled), Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        fun newInstance(): BaseFragment {
            return InviteFriendsFragment()
        }
    }
}