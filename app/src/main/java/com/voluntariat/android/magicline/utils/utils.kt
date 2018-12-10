package com.voluntariat.android.magicline.utils

import com.voluntariat.android.magicline.BuildConfig

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