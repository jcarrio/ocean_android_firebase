package com.example.ocean_android_firebase

import com.example.ocean_android_firebase.BuildConfig.FIREBASE_KEY

class Constants {

    companion object {
        const val BASE_URL = "https://fcm.googleapis.com"
        // Salve sua chave FIREBASE_KEY no arquivo local.properties
        // Fonte: Firebase site / Project settings / Cloud messaging / Server key
        const val SERVER_KEY = "$FIREBASE_KEY"
        const val CONTENT_TYPE = "application/json"
    }
}