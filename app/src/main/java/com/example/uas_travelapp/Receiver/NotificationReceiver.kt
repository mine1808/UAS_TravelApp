package com.example.uas_travelapp.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class NotificationReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val msg = intent?.getStringExtra("MESSAGE")
        Log.d("Notifikasi", msg.toString())
        if (msg != null) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}
