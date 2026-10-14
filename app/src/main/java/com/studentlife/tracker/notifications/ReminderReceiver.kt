package com.studentlife.tracker.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.studentlife.tracker.R
import com.studentlife.tracker.ui.main.MainActivity
import com.studentlife.tracker.utils.NotificationHelper

/**
 * Broadcast receiver that fires the daily tracking reminder notification.
 */
class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val tapIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("navigate_to", "today")
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            tapIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Student Life — Daily Check-in")
            .setContentText("Don't forget to record today's activities and progress! 📋")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Take a few minutes to record today's activities. Your 30-day journey awaits. Observe Yourself → Understand Yourself → Improve Yourself.")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NotificationHelper.NOTIFICATION_ID, notification)
    }
}
