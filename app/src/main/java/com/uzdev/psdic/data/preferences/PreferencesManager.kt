package com.uzdev.psdic.data.preferences

import android.content.Context
import androidx.preference.PreferenceManager
import com.uzdev.psdic.util.Constants

class PreferencesManager(
    private val context: Context
) {
    private val preferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(
            context
        )
    }
    var fillRequired by PreferencesDelegate(
        preferences,
        Constants.FILL_REQUIRED_IN_LANG,
        "To'ldirish shart"
    )
    var yourLanguage by PreferencesDelegate(preferences, Constants.YOUR_LANG, "O'zbekcha")
    var isIntroduced by PreferencesDelegate(preferences, Constants.IS_INTRODUCED, false)


}