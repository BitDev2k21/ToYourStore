package com.toyourstore

import android.app.Application
import android.util.Log
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.toyourstore.preference.SessionData

class ToYourStore : Application() {

    override fun onCreate() {
        super.onCreate()
//        FacebookSdk.sdkInitialize(applicationContext)
//        AppEventsLogger.activateApp(this)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.v("@@@", "FirebaseMessaging Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            SessionData.getInstance(this).saveFirebaseToken(token)
            // Log and toast
            val msg = ""
            Log.v("@@@","FirebaseMessaging token : $token" )
        })

        FacebookSdk.sdkInitialize(this)
        AppEventsLogger.activateApp(this);
    }
}