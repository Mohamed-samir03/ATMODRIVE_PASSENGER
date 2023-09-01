package com.mosamir.atmodrivepassenger.util

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesManager (context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(Constants.PASSENGER_PREFS, Context.MODE_PRIVATE)

    public fun saveString(key: String, value: String) {
        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }

    public fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    public fun clearString(key: String) {
        sharedPreferences.edit()
            .remove(key)
            .apply()
    }

}