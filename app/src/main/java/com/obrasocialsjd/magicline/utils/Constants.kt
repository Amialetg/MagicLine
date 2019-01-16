package com.obrasocialsjd.magicline.utils

import com.obrasocialsjd.magicline.R

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
const val IS_MODAL: String = "isModal"


/**
 * ROOM QUERIES
 */

const val TEAM_MARKERS_QUERY : String = "select SUM(total) as total,  " +
        " SUM(total * 100 / percentage) as spots " +
        "from teamsMarkers where city = :city"

/**
 * MAP
 */
const val KM: String = "km"

/**
 * ONESIGNAL
 */
const val LOCATION: String = "location"
const val LAST_NEWS: String = "ultimaNoticia"
const val DONATION: String = "ferDonacio"
const val EVENT: String = "detallsEsdeveniments"
const val FROM: String = "From"
const val INITIAL_POSITION : Int = 0


/**
 * DONATION
 */
const val JS = "var style = document.createElement('style'); style.innerHTML = 'header, #results, body > div > h3 { display: none; }'; document.head.appendChild(style);"

/**
 * PERMISSIONS CONSTANTS
 */

const val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 13

/**
 * COLORS
 */

const val MAP_BUTTON_SELECTED = R.color.colorPrimary
const val MAP_BUTTON_UNSELECTED = R.color.grey