package com.obrasocialsjd.magicline.activities.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.models.DetailModel
import com.obrasocialsjd.magicline.utils.Fragment
import com.obrasocialsjd.magicline.utils.SHARE_DONATIONS
import com.obrasocialsjd.magicline.utils.openShareActivity
import com.obrasocialsjd.magicline.utils.shareApp
import kotlinx.android.synthetic.main.fragment_share.*
import kotlinx.android.synthetic.main.fragment_share.view.*
import kotlinx.android.synthetic.main.layout_share.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*


class ShareFragment: BaseFragment() {
    private lateinit var shareView: View
    private var isShareDonations: Boolean = true
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
        if (isShareDonations) { shareView.textViewHeader.text = getText(R.string.shareDonationsTitle) } else { shareView.textViewHeader.text = getText(R.string.inviteFriends) }
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        shareView.crossBtn.setBackgroundResource(R.drawable.ic_white_cross)
        shareView.crossBtn.setOnClickListener { this.requireActivity().onBackPressed() }
    }

    private fun initRRSSListeners(){
        shareText = if (isShareDonations) getString(R.string.shareDonationsText)
        else getString(R.string.shareAppText)
        val storeLink: String = getString(R.string.storeLink)

        shareView.emailCell.setOnClickListener {
            activity?.openShareActivity(shareApp("", shareText, storeLink))
        }
        shareView.fbCell.setOnClickListener {
            activity?.openShareActivity(shareApp(getString(R.string.fb_pkg), shareText, storeLink))
        }
        shareView.msnCell.setOnClickListener {
            activity?.openShareActivity(shareApp(getString(R.string.msn_pkg), shareText, storeLink))
        }
        shareView.telegramCell.setOnClickListener {
            activity?.openShareActivity(shareApp(getString(R.string.tel_pkg), shareText, storeLink))
        }
        shareView.whatsCell.setOnClickListener {
            activity?.openShareActivity(shareApp(getString(R.string.whats_pkg), shareText, storeLink))
        }

    }

    companion object {
        fun newInstance(shareDonation: Boolean): BaseFragment {
            val myFragment = ShareFragment()
            val args = Bundle()
            args.putSerializable(SHARE_DONATIONS, shareDonation)
            myFragment.arguments = args
            return ShareFragment()
        }
    }
}