package com.obrasocialsjd.magicline.activities.main.fragments

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.obrasocialsjd.magicline.R
import kotlinx.android.synthetic.main.fragment_donations.view.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.view.*

class DonationsFragment: BaseFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val css = "header, #results, body > div > h3 { display: none; }"
        val js = "var style = document.createElement('style'); style.innerHTML = '$css'; document.head.appendChild(style);"

     val v: View = inflater.inflate(R.layout.fragment_donations, container,  false)


        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        v.topToolbar.title = getString(R.string.donation_var_title)
        if(this.tag == "isModal") v.topToolbar.navigationIcon = ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_black_cross)
        v.topToolbar.setNavigationOnClickListener { this.requireActivity().onBackPressed() }


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
                v.webviewDonation.evaluateJavascript(js, null)
                super.onPageStarted(view, url, favicon)

            }

            override fun onLoadResource(view: WebView?, url: String?) {
                v.webviewDonation.evaluateJavascript(js, null)
                super.onLoadResource(view, url)
            }

            override fun onPageFinished(view: WebView, url: String) {
                // Page loading finished
                v.progressBar.visibility = View.GONE
                v.webviewDonation.evaluateJavascript(js, null)
                v.progressBar.invalidate()
                super.onPageFinished(view, url)
            }
        }
         v.webviewDonation.loadUrl(getString(R.string.donation_url))
        v.webviewDonation.clearCache(true)
        return v
    }


    private fun testApi() {
        /*val loginModelClient = OkHttpClient().newBuilder()
                .addInterceptor(MagicLineInterceptor("acces_token"))
                .build()
        val magicLineService = retrofit.create(MagicLineService::class.java)
        val call = magicLineService.testAPI("Test")
        val result = call.execute().body()
        Log.e("API",result.toString())*/
    }

    companion object {
        fun newInstance() : BaseFragment {
            return DonationsFragment()
        }
    }
}