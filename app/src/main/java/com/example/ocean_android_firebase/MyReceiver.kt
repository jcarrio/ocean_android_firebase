package com.example.ocean_android_firebase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent?.action) {
            "com.example.ocean_android_firebase.ADD_RECEIVED" -> Log.d("BROADCAST","add")  // tvMessages.text += 1
            "com.example.ocean_android_firebase.CLEAR_RECEIVED" -> Log.d("BROADCAST","clear") // tvMessages.text = "0"
        }
    }
}