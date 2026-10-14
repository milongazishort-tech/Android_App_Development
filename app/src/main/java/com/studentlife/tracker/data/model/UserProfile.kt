package com.studentlife.tracker.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

/**
 * User profile model stored in Firestore under users/{userId}
 */
data class UserProfile(
    @DocumentId
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val university: String = "",
    val department: String = "",
    val startDate: Date? = null,
    val endDate: Date? = null,
    val profileImageUrl: String = "",
    @ServerTimestamp
    val createdAt: Date? = null,
    val notificationsEnabled: Boolean = true,
    val targetWakeTime: String = "06:00",
    val targetSleepTime: String = "23:00",
    val reminderHour: Int = 20,
    val reminderMinute: Int = 0
)
