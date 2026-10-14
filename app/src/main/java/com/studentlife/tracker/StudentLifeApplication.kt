package com.studentlife.tracker

import android.app.Application
import com.studentlife.tracker.utils.NotificationHelper

/**
 * Application class for app-wide initialization.
 */
class StudentLifeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Create notification channel on app start
        NotificationHelper.createNotificationChannel(this)
    }
}
