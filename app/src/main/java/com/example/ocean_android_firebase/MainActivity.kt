package com.example.ocean_android_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.view.View
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TOPIC = "/topics/myTopic"

class MainActivity : AppCompatActivity() {

    // Cria constantes
    val ADD_RECEIVED = "com.example.ocean_android_firebase.ADD_RECEIVED"
    val CLEAR_RECEIVED = "com.example.ocean_android_firebase.CLEAR_RECEIVED"
    val TAG = "MainActivity"

    // lateinit define que o valor será inicilizado depois
    lateinit var tvMessages: TextView
    var receiver: BroadcastReceiver? = null

    override fun onResume() {
        super.onResume()
        if (receiver == null)
            receiver = MyReceiver()
        val filter = IntentFilter()
        filter.addAction(ADD_RECEIVED)
        filter.addAction(CLEAR_RECEIVED)
        registerReceiver(receiver, filter)
    }

    override fun onPause() {
        super.onPause()
        if (receiver != null) {
            unregisterReceiver(receiver)
            receiver = null
        }
    }

    private fun updateUI(intent: Intent) {
        when (intent.action) {
            ADD_RECEIVED -> tvMessages.text = (tvMessages.text.toString().toInt()+1).toString()
            CLEAR_RECEIVED -> tvMessages.text = "0"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        tvMessages = findViewById<TextView>(R.id.tvMessages)
        val btEnviar = findViewById<Button>(R.id.btEnviar)

        tvMessages.text = "0"

        btEnviar.setOnClickListener {
            val title = "Notificação interna"
            val message = "Testando notificação gerada pelo app"
            if(title.isNotEmpty() && message.isNotEmpty() && TOPIC.isNotEmpty()) {
                PushNotification(
                    NotificationData(title, message),
                    TOPIC
                ).also {
                    sendNotification(it)
                }
            }
        }
    }

    // Button Limpar, associado diretamente no xml
    fun broadcastLimpar(view: View) {
        val intent = Intent()
        intent.action = CLEAR_RECEIVED
        intent.flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
        sendBroadcast(intent)
    }

    // inner possibilita acessar métodos da classe pai
    inner class MyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateUI(intent)
        }
    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            } else {
                Log.e(TAG, response.errorBody().toString())
            }
        } catch(e: Exception) {
            Log.e(TAG, e.toString())
        }
    }
}