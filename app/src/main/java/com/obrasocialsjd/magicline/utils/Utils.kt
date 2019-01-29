package com.obrasocialsjd.magicline.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.obrasocialsjd.magicline.BuildConfig
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.activities.main.fragments.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun getFlavor() = BuildConfig.FLAVOR.capitalize()

fun <T> callback(success: ((Response<T>) -> Unit)?, failure: ((t: Throwable) -> Unit)? = null): Callback<T> {
    return object : Callback<T> {
        override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) { success?.invoke(response) }
        override fun onFailure(call: Call<T>, t: Throwable) { failure?.invoke(t) }
    }
}

fun AppCompatActivity.transitionWithModalAnimation(context: Context, fragment: BaseFragment, useModalAnimation: Boolean = true, addToBackStack: Boolean = true, analyticsScreen : TrackingUtils.Screens) {
    // Analytics
    TrackingUtils(context).track(analyticsScreen)
    val transaction = this.supportFragmentManager.beginTransaction()

    // Adds button bar management (show/hide) bundle's argument
    val bundle = fragment.arguments ?: Bundle()
    bundle.putBoolean(SHOW_BOTTOM_BAR_TAG, useModalAnimation)
    fragment.arguments = bundle

    if(useModalAnimation) transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up, R.anim.slide_in_down, R.anim.slide_out_down)

    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
        if (useModalAnimation) transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up, R.anim.slide_in_down, R.anim.slide_out_down)
    }

    if (useModalAnimation) {
        transaction.replace(R.id.frame_layout, fragment, IS_MODAL)
    }
    else {
        transaction.replace(R.id.frame_layout, fragment)
    }

    if(addToBackStack) transaction.addToBackStack(fragment.javaClass.canonicalName)
    transaction.commit()

    supportFragmentManager.executePendingTransactions()
}

fun Activity.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}

fun Activity.funNotAvailableDialog() {
    let {context ->
        val builder = AlertDialog.Builder(context)
        builder.setMessage(R.string.notAvailableText)
        builder.setNeutralButton(R.string.closeText){ _, _->}
        val dialog = builder.create()

        dialog.show()
    }
}

fun Activity.notAvailableDialog(title : Int= R.string.gps_not_available_title, message: Int = R.string.gps_not_available_text) {
    let {context ->
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setNeutralButton(R.string.closeText){_,_->}
        val dialog = builder.create()
        dialog.show()
    }
}

fun String.htmlToSpanned() : Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}

fun Double.addCurrency() : String {
    return this.addThousandsSeparator().plus(getCurrency())
}

fun String.addThousandsSeparator() : String {
    return this.toDouble().addThousandsSeparator()
}

fun Double.addThousandsSeparator() : String {
    val decimalFormat = DecimalFormat("###,###,###", DecimalFormatSymbols.getInstance(Locale.getDefault()))
    return decimalFormat.format(this)
}

fun shareApp(pkg: String, shareText: String = "", isShareDonations: Boolean = false): Intent {
    val storeLink = STORE_LINK
    val waIntent = Intent(Intent.ACTION_SEND)
    waIntent.type = "text/plain"
    waIntent.putExtra(Intent.EXTRA_SUBJECT, "Magic Line")
    var text = "\n" +  shareText + "\n\n"

    if (!isShareDonations){
        text = text + storeLink  + BuildConfig.APPLICATION_ID
    }

    if (pkg.isNotEmpty()) waIntent.setPackage(pkg)
    
    waIntent.putExtra(Intent.EXTRA_TEXT, text)
    return waIntent
}

fun Activity.openShareActivity (intent : Intent) {
    startActivity(Intent.createChooser(intent, "Share with"))
}