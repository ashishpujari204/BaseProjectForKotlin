package com.ashish.baseproject.util

import android.app.Activity
import com.ashish.baseproject.storage.SharedPreferencesStorage

class Constants {

    companion object {

        const val font_name = "Fonts/google_sans_regular.ttf"
        const val PERFERENCES_NAME = "baseProject"

        const val DEFAULT_VALUE = "NA"

        /**
         * Json key
         */

        const val SUCCESS = "success"
        const val RESULT = "result"
        const val CONVERSATION_RATES = "conversion_rates"

        private const val FIRST_NAME = "first_name"
        private const val LAST_NAME = "last_name"
        const val  KEYSTORE_HELPER_ALIES = "baseProject";


        fun saveFirstName(activity: Activity, fromCode: String) {
            SharedPreferencesStorage(activity).setString(FIRST_NAME, fromCode)
        }

        fun saveLastName(activity: Activity, toCode: String) {
            SharedPreferencesStorage(activity).setString(LAST_NAME, toCode)
        }

        fun getFirstName(activity: Activity): String {
            return SharedPreferencesStorage(activity).getString(FIRST_NAME)
        }

        fun getLastName(activity: Activity): String {
            return SharedPreferencesStorage(activity).getString(LAST_NAME)
        }

    }

}