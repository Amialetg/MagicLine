package com.voluntariat.android.magicline.models

import com.voluntariat.android.magicline.R
import java.io.Serializable

//By default the colors of the toolbar are black, if you want to set it to white you have to use the "isBlack" boolean when you call the DetailModel()
data class DetailModel(val title: String, val subtitle: String, val textBody: String, val link: String, val toolbarImg: Int = R.drawable.about_us, val isBlack: Boolean = true, val hasToolbarImg: Boolean = false) : Serializable