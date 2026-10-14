package com.studentlife.tracker.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.studentlife.tracker.utils.NotificationHelper
import com.studentlife.tracker.utils.PrefsManager

/**
 * Re-schedules daily reminder after device reboot.
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            if (PrefsManager.isNotificationsEnabled(context)) {
                val hour = PrefsManager.getReminderHour(context)
                val minute = PrefsManager.getReminderMinute(context)
                NotificationHelper.scheduleDailyReminder(context, hour, minute)
            }
        }
    }
}
