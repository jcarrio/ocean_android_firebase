package com.example.ocean_android_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.view.View

class MainActivity : AppCompatActivity() {

    lateinit var tvMessages: TextView
    var receiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureReceiver()

        tvMessages = findViewById<TextView>(R.id.tvMessages)
        val btEnviar = findViewById<Button>(R.id.btEnviar)

        btEnviar.setOnClickListener {
/*            val notification = RemoteMessage.notification
            notification?.let {
                pushNotification(it.title, it.body)
 */
        }
    }

    fun broadcastLimpar(view: View) {
        val intent = Intent()
        intent.action = "com.example.ocean_android_firebase.CLEAR_RECEIVED"
        intent.flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    private fun configureReceiver() {
        val filter = IntentFilter()
        filter.addAction("com.example.ocean_android_firebase.ADD_RECEIVED")
        filter.addAction("com.example.ocean_android_firebase.CLEAR_RECEIVED")
        receiver = MyReceiver()
        registerReceiver(receiver, filter)
    }
}