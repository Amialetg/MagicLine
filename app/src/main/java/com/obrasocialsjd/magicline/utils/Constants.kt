package com.obrasocialsjd.magicline.utils

/**
 * URLS
 */

const val URL_IDEAS_GUIDE: String = "http://www.magiclinesjd.org/files/froala/74e5144938f7c849173fe0347e213fd8052d5731.pdf"

/**
 * FLAVORS
 */
const val BARCELONA: String = "Barcelona"
const val VALENCIA: String = "Valencia"
const val MALLORCA: String = "Mallorca"

/**
 * PREFERENCES
 */

const val PREF_SETTINGS: String = "Settings"
const val PREF_LANGUAGE : String = "My_Lang"

/**
 * LOCALE
 */

const val SPANISH : String = "es_ES"

const val CATALAN_API : String = "cat"
const val SPANISH_API : String = "spa"

const val SPANISH_LOCALE : String = "es"

const val EURO : String = "€"

/**
 * BOTTONAVIGATIONBAR ITEM POSITIONS
 */

const val HOME : Int = 0
const val SCHEDULE : Int = 1
const val DONATIONS : Int = 3
const val OPTIONS : Int = 4

/** VIEW MANAGEMENT TAGS**/
const val SHOW_BOTTOM_BAR_TAG : String = "SHOW_BOTTOM_BAR"
const val SHOW_SHARE_VIEW_TAG : String = "SHOW_SHARE_VIEW"

/**
 * ROOM QUERIES
 */

const val TEAM_MARKERS_QUERY : String = "select SUM(total) as total,  " +
        " SUM(total * 100 / percentage) as spots " +
        "from teamsMarkers where city = :city"

/**
 * ONESIGNAL
 */
const val LOCATION: String = "location"

/**
 * DONATION
 */

const val CSS = "header, #results, body > div > h3 { display: none; }"
const val JS = "var style = document.createElement('style'); style.innerHTML = '$CSS'; document.head.appendChild(style);"
