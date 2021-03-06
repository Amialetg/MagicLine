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
const val PREF_USER_LOCATION : String = "PREF_USER_LOCATION"

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

/**
 * VALUES
 * **/

const val MILLISECONDS : Long = 3600000
const val MILLIS : Long = 1000
const val ZERO : String = "00"

const val COLON : String = ":"
const val PERIOD : String = "."

/**
 * SCHEDULE ADAPTER
 */
const val TYPE_SCHEDULE_TITLE_FIRST : Int = 0
const val TYPE_COMMON_CARD : Int = 1
const val TYPE_SCHEDULE_TITLE_COMMON : Int = 2
const val TYPE_LAST_CARD : Int = 3
const val TYPE_FIRST_CARD : Int = 4
const val TYPE_SCHEDULE_TITLE_COMMON_NO_BUTTON : Int = 5
const val TYPE_SCHEDULE_TITLE_COMMON_LAST : Int = 6



/**
 * VARIABLES
 */

const val SHARE_DONATIONS = "shareDonations"
const val STORE_LINK = "https://play.google.com/store/apps/details?id="
const val SPOTS = "13000"
const val INDEX_STBOI = 3
const val INDEX_40KM = 5
const val INDEX_10KM = 0

/**
 * * FRAGMENT NAME
 */

enum class Fragment {
    Detail,
    Donations,
    ShareApp,
    MagicLine,
    Map,
    MoreInfo,
    AboutApp,
    Options,
    Schedule
}