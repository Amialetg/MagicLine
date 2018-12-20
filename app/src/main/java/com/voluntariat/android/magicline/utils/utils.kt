package com.voluntariat.android.magicline.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.appcompat.app.AppCompatActivity
import com.voluntariat.android.magicline.BuildConfig
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.activities.main.fragments.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val BARCELONA: String = "Barcelona"
const val VALENCIA: String = "Valencia"
const val MALLORCA: String = "Mallorca"
//todo: mover a constants.kt
const val URL_IDEAS_GUIDE: String = "http://www.magiclinesjd.org/files/froala/74e5144938f7c849173fe0347e213fd8052d5731.pdf"

// To use an specific code for each Flavor :
fun isBarcelonaFlavor() : Boolean {
    return BuildConfig.FLAVOR.equals(BARCELONA, true)
}
fun isValenciaFlavor() : Boolean {
    return  BuildConfig.FLAVOR.equals(VALENCIA, true)
}
fun isMallorcaFlavor() : Boolean {
    return  BuildConfig.FLAVOR.equals(MALLORCA, true)
}

fun String.capitalizeFirstLetter() : String = this.toLowerCase().capitalize()

fun <T> callback(success: ((Response<T>) -> Unit)?, failure: ((t: Throwable) -> Unit)? = null): Callback<T> {
    return object : Callback<T> {
        override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) { success?.invoke(response) }
        override fun onFailure(call: Call<T>, t: Throwable) { failure?.invoke(t) }
    }
}

fun AppCompatActivity.transitionWithModalAnimation(fragment: BaseFragment, useModalAnimation: Boolean = true) {
    val transaction = this.supportFragmentManager.beginTransaction()
    if(useModalAnimation) transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up, R.anim.slide_in_down, R.anim.slide_out_down)
    transaction.replace(R.id.frame_layout, fragment)
    transaction.addToBackStack(fragment.javaClass.canonicalName)
    transaction.commit()
}

fun String.htmlToSpanned() : Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}