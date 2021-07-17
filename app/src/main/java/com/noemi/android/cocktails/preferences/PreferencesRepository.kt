package com.noemi.android.cocktails.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {

    var cocktailId: Int
        set(value) {
            sharedPreferences.edit().putInt(KEY_COCKTAIL_ID, value).apply()
        }
        get() = sharedPreferences.getInt(KEY_COCKTAIL_ID, -1)

    companion object {
        private const val KEY_COCKTAIL_ID = "key_cocktail_id"
    }
}