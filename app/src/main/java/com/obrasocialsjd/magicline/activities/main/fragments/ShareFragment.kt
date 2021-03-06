package com.obrasocialsjd.magicline.activities.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.utils.SHARE_DONATIONS
import com.obrasocialsjd.magicline.utils.openShareActivity
import com.obrasocialsjd.magicline.utils.shareApp
import kotlinx.android.synthetic.main.fragment_share.view.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*


class ShareFragment: BaseFragment() {
    private lateinit var shareView: View
    private var isShareDonations : Boolean = false
    private lateinit var shareText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShareDonations = arguments?.getBoolean(SHARE_DONATIONS) as Boolean
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        shareView = inflater.inflate(R.layout.fragment_share, container, false)
        initToolbar()
        initRRSSListeners()
        return shareView
    }

    override fun onResume() {
        super.onResume()

        if (isShareDonations) {
            shareView.textViewHeader.text = getText(R.string.shareDonationsTitle)
            shareText = getString(R.string.shareDonationsText)
        }
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        shareView.crossBtn.setBackgroundResource(R.drawable.ic_white_cross)
        shareView.crossBtn.setOnClickListener { this.requireActivity().onBackPressed() }

        shareView.textViewHeader.text = getText(R.string.inviteFriends)
        shareText = getString(R.string.shareAppText)
    }

    private fun initRRSSListeners(){

        shareView.emailCell.setOnClickListener {
            activity?.openShareActivity(shareApp("", shareText, isShareDonations))
        }
        shareView.fbCell.setOnClickListener {
            activity?.openShareActivity(shareApp(getString(R.string.fb_pkg), shareText, isShareDonations))
        }
        shareView.msnCell.setOnClickListener {
            activity?.openShareActivity(shareApp(getString(R.string.msn_pkg), shareText, isShareDonations))
        }
        shareView.telegramCell.setOnClickListener {
            activity?.openShareActivity(shareApp(getString(R.string.tel_pkg), shareText, isShareDonations))
        }
        shareView.whatsCell.setOnClickListener {
            activity?.openShareActivity(shareApp(getString(R.string.whats_pkg), shareText, isShareDonations))
        }

    }

    companion object {
        fun newInstance(shareDonations: Boolean): BaseFragment {
            val myFragment = ShareFragment()
            val args = Bundle()
            args.putBoolean(SHARE_DONATIONS, shareDonations)
            myFragment.arguments = args
            return myFragment
        }
    }
}