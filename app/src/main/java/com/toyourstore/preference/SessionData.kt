package com.toyourstore.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.toyourstore.utils.Constant

class SessionData(context: Context) {

    var context: Context;
    var prefs: SharedPreferences;
    val MY_PREFS_NAME = "YourStore"
    val editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit()

    init {
        this.context = context
        prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    }

    companion object {
        var sessionData: SessionData? = null
        fun getInstance(context: Context): SessionData {
            if (sessionData == null) {
                sessionData = SessionData(context)
            }
            return sessionData as SessionData
        }
    }

    fun saveToken(status: String) {
        editor.putString(Constant.SAVE_TOKEN, status)
        editor.commit()
    }

    fun saveFirebaseToken(status: String) {
        editor.putString(Constant.FIREBASE_TOKEN, status)
        editor.commit()
    }

    fun getToken(): String? {
        return prefs.getString(Constant.SAVE_TOKEN, "")
    }

    fun getFirebaseToken(): String? {
        return prefs.getString(Constant.FIREBASE_TOKEN, "")
    }

    fun saveUserId(status: String) {
        editor.putString(Constant.USER_ID, status)
        editor.commit()
    }

    fun getUserId(): String? {
        return prefs.getString(Constant.USER_ID, "")
    }

    fun saveIsRegister(status: Boolean) {
        editor.putBoolean(Constant.REGISTRE_DISTRIBUTE, status)
        editor.commit()
    }

    fun isRegisterDis(): Boolean {
        return prefs.getBoolean(Constant.REGISTRE_DISTRIBUTE, false)
    }


    fun clearData() {
        editor.clear()
        editor.commit()
        editor.apply()
        editor.putBoolean(Constant.REGISTRE_DISTRIBUTE, false)
        editor.commit()
       }


}


