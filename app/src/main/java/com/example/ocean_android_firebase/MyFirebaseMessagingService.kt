package com.example.ocean_android_firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Idem abaixo (comentado->apagar)
        //super.onMessageReceived(p0)
        Log.d("FIREBASE", "Message received:: $remoteMessage")
    }

    override fun onNewToken(token: String) {
        // Clicar em onNewToken com CTRL+botão direito
        // Se no código não faz nada, não precisa chamar (comentado->apagar)
        //super.onNewToken(p0)
        Log.d("FIREBASE", "Token refreshed: $token")
    }
}