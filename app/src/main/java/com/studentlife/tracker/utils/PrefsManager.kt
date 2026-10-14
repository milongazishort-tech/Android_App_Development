package com.studentlife.tracker.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Utility for managing SharedPreferences.
 */
object PrefsManager {

    private const val PREFS_NAME = "student_life_prefs"

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setNotificationsEnabled(context: Context, enabled: Boolean) {
        prefs(context).edit().putBoolean("notifications_enabled", enabled).apply()
    }

    fun isNotificationsEnabled(context: Context): Boolean =
        prefs(context).getBoolean("notifications_enabled", true)

    fun setReminderTime(context: Context, hour: Int, minute: Int) {
        prefs(context).edit()
            .putInt("reminder_hour", hour)
            .putInt("reminder_minute", minute)
            .apply()
    }

    fun getReminderHour(context: Context): Int =
        prefs(context).getInt("reminder_hour", 20)

    fun getReminderMinute(context: Context): Int =
        prefs(context).getInt("reminder_minute", 0)

    fun setTargetWakeTime(context: Context, time: String) {
        prefs(context).edit().putString("target_wake_time", time).apply()
    }

    fun getTargetWakeTime(context: Context): String =
        prefs(context).getString("target_wake_time", "06:00") ?: "06:00"

    fun setTargetSleepTime(context: Context, time: String) {
        prefs(context).edit().putString("target_sleep_time", time).apply()
    }

    fun getTargetSleepTime(context: Context): String =
        prefs(context).getString("target_sleep_time", "23:00") ?: "23:00"

    fun isOnboardingCompleted(context: Context): Boolean =
        prefs(context).getBoolean("onboarding_completed", false)

    fun setOnboardingCompleted(context: Context, completed: Boolean) {
        prefs(context).edit().putBoolean("onboarding_completed", completed).apply()
    }
}
