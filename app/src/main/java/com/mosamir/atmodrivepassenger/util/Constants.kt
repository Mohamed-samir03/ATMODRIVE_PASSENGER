package com.mosamir.atmodrivepassenger.util

import com.google.android.gms.maps.model.LatLng

class Constants {

    companion object{

        const val BASE_URL = "https://s1.drive.api.atmosphere.solutions/api/v1/passengers/"

        const val BASE_Image_URL = "https://s1.drive.dashboard.atmosphere.solutions/storage/"

        const val INTRO_PREFS = "firstTime"

        var isBottomSheetOn = false
        var pickUpLatLng: LatLng? = null
        var dropOffLatLng: LatLng? = null

        // SharedPreferences keys
        const val PASSENGER_PREFS = "PassengerPrefs"
        const val PASSENGER_ID_PREFS = "PassengerId"
        const val AVATAR_PREFS = "avatar"
        const val EMAIL_PREFS = "email"
        const val FULL_NAME_PREFS = "full_name"
        const val IS_DARK_MODE_PREFS = "is_dark_mode"
        const val LANG_PREFS = "lang"
        const val MOBILE_PREFS = "mobile"
        const val PASSENGER_CODE_PREFS = "passenger_code"
        const val RATE_PREFS = "rate"
        const val REMEMBER_TOKEN_PREFS = "remember_token"
        const val SHAKE_PHONE_PREFS = "shake_phone"
        const val STATUS_PREFS = "status"
        const val SUSPEND_PREFS = "suspend"

    }

}