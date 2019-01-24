package com.obrasocialsjd.magicline.activities.main.fragments

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.utils.IS_MODAL
import com.obrasocialsjd.magicline.utils.JS
import com.obrasocialsjd.magicline.utils.notAvailableDialog
import kotlinx.android.synthetic.main.fragment_donations.view.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.view.*

class DonationsFragment: BaseFragment(){
    lateinit var v: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(isConnected()) {
             v = inflater.inflate(R.layout.fragment_donations, container,  false)

            val settings = v.webviewDonation.settings
            settings.javaScriptEnabled = true //OJO

            v.webviewDonation.webViewClient = WebViewClient()
            v.webviewDonation.webChromeClient = WebChromeClient()

            // Enable zooming in web view
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = true

            // Enable disable images in web view
            settings.blockNetworkImage = false
            // Whether the WebView should load image resources
            settings.loadsImagesAutomatically = true

            settings.domStorageEnabled = true

            //necessary to load images
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            }
            // Set web view client
            v.webviewDonation.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                    // Page loading started
                    v.webviewDonation.evaluateJavascript(JS, null)
                    super.onPageStarted(view, url, favicon)

                }

                override fun onLoadResource(view: WebView?, url: String?) {
                    v.webviewDonation.evaluateJavascript(JS, null)
                    super.onLoadResource(view, url)
                }

                override fun onPageFinished(view: WebView, url: String) {
                    // Page loading finished
                    v.progressBar.visibility = View.GONE
                    v.webviewDonation.evaluateJavascript(JS, null)
                    v.progressBar.invalidate()
                    super.onPageFinished(view, url)
                }
            }
            v.webviewDonation.loadUrl(getString(R.string.donation_url))
            v.webviewDonation.clearCache(true)
        }else{
             v =  inflater.inflate(R.layout.layout_no_wifi_webview, container,  false)
        }

        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        v.topToolbar.title = getString(R.string.donation_var_title)
        if(this.tag == IS_MODAL) v.topToolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_black_cross)
        v.topToolbar.setNavigationOnClickListener { this.requireActivity().onBackPressed() }

        return v
    }

    companion object {
        fun newInstance() : BaseFragment {
            return DonationsFragment()
        }
    }

    private fun isConnected(): Boolean {
        val conMgr = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo

        if (netInfo == null || !netInfo.isConnected || !netInfo.isAvailable) {
            return false
        }
        return true
    }
}