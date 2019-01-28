package com.obrasocialsjd.magicline.activities.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.models.DetailModel
import com.obrasocialsjd.magicline.utils.Fragment
import com.obrasocialsjd.magicline.utils.openShareActivity
import com.obrasocialsjd.magicline.utils.shareApp
import kotlinx.android.synthetic.main.fragment_share.*
import kotlinx.android.synthetic.main.fragment_share.view.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*


class ShareFragment: BaseFragment() {
    private lateinit var shareView: View
    private var isShareDonations: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShareDonations = arguments?.get(Fragment.ShareApp.toString()) as Boolean
    }

    override fun onStart() {
        super.onStart()
        initToolbar()
        initRRSSListeners()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        shareView = inflater.inflate(R.layout.fragment_share, container, false)
        return shareView

    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        shareView.crossBtn.setBackgroundResource(R.drawable.ic_white_cross)
        shareView.crossBtn.setOnClickListener { this.requireActivity().onBackPressed() }
        if (isShareDonations) { shareView.textViewHeader.setText(R.string.inviteFriends) } else { shareView.textViewHeader.setText(getText(R.string.shareDonationsTitle))}
    }

    private fun initRRSSListeners(){
        val shareText: String = getString(R.string.shareAppText)
        val storeLink: String = getString(R.string.storeLink)

        emailCell.setOnClickListener {
            activity?.openShareActivity(shareApp("", shareText, storeLink))
        }
        fbCell.setOnClickListener {
            activity?.openShareActivity(shareApp(getString(R.string.fb_pkg), shareText, storeLink))
        }
        msnCell.setOnClickListener {
            activity?.openShareActivity(shareApp(getString(R.string.msn_pkg), shareText, storeLink))
        }
        telegramCell.setOnClickListener {
            activity?.openShareActivity(shareApp(getString(R.string.tel_pkg), shareText, storeLink))
        }
        whatsCell.setOnClickListener {
            activity?.openShareActivity(shareApp(getString(R.string.whats_pkg), shareText, storeLink))
        }

    }

    companion object {
        fun newInstance(isShareDonation: Boolean): BaseFragment {
            val myFragment = ShareFragment()
            val args = Bundle()
            args.putSerializable(Fragment.ShareApp.toString(), isShareDonation)
            myFragment.arguments = args
            return ShareFragment()
        }
    }
}