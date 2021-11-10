package com.sebrahimi.motiontagdemo

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TransitionIntentService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}
