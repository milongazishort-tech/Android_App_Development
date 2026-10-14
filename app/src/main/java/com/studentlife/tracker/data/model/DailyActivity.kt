package com.studentlife.tracker.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

/**
 * Daily activity record stored in Firestore under users/{userId}/activities/{date}
 * Each field represents one of the 12 tracked activities.
 */
data class DailyActivity(
    @DocumentId
    val activityId: String = "",
    val userId: String = "",
    val date: String = "",          // Format: "yyyy-MM-dd"
    val dayNumber: Int = 0,         // 1–30

    // 1. Early Morning / Wake-up (0–10 points)
    val wakeEarly: Boolean = false,
    val wakeTime: String = "",      // Actual wake time recorded

    // 2. University Attendance (0–10 points)
    val universityAttended: Boolean = false,

    // 3. Class Participation (0–10 points)
    val classesAttended: Int = 0,
    val totalClasses: Int = 5,

    // 4. Religious / Personal Discipline (0–10 points)
    val personalRoutine: Boolean = false,

    // 5. Social Interaction (0–5 points)
    val meaningfulInteractions: Int = 0,  // 0-5 meaningful interactions

    // 6. Daily Learning (0–10 points)
    val dailyLearning: String = "",       // What was learned today
    val learningDone: Boolean = false,

    // 7. Physical Activity (0–10 points)
    val physicalActivity: String = "",    // Description
    val physicalActivityMinutes: Int = 0, // Duration in minutes

    // 8. Earning / Skill Development (0–10 points)
    val skillDevelopment: String = "",    // Description
    val skillDevelopmentMinutes: Int = 0, // Duration in minutes

    // 9. Class Work Recap (0–10 points)
    val classRecap: Boolean = false,

    // 10. Social Media / News Usage (0–5 points, inverted - less is more)
    val socialMediaUsage: String = "moderate",  // "minimal", "moderate", "excessive"
    val socialMediaMinutes: Int = 0,

    // 11. Self-Reflection (0–10 points)
    val reflection: String = "",
    val proudOf: String = "",
    val improveTomorrow: String = "",
    val learnedToday: String = "",

    // 12. Sleeping on Time (0–10 points)
    val sleepOnTime: Boolean = false,
    val actualSleepTime: String = "",

    // Score
    val dailyScore: Double = 0.0,
    val isCompleted: Boolean = false,

    @ServerTimestamp
    val updatedAt: Date? = null
) {
    /**
     * Calculate the daily score based on all recorded activities.
     * Maximum total points = 100
     */
    fun calculateScore(): Double {
        var totalPoints = 0.0
        val maxPoints = 100.0

        // 1. Wake Early (10 points)
        if (wakeEarly) totalPoints += 10.0

        // 2. University Attendance (10 points)
        if (universityAttended) totalPoints += 10.0

        // 3. Class Participation (10 points) - proportional
        if (totalClasses > 0) {
            totalPoints += (classesAttended.toDouble() / totalClasses) * 10.0
        }

        // 4. Personal Routine / Religious Discipline (10 points)
        if (personalRoutine) totalPoints += 10.0

        // 5. Social Interaction (5 points) - capped at 5 interactions
        totalPoints += minOf(meaningfulInteractions.toDouble(), 5.0)

        // 6. Daily Learning (10 points)
        if (learningDone && dailyLearning.isNotBlank()) totalPoints += 10.0
        else if (dailyLearning.isNotBlank()) totalPoints += 5.0

        // 7. Physical Activity (10 points) - based on duration
        when {
            physicalActivityMinutes >= 45 -> totalPoints += 10.0
            physicalActivityMinutes >= 30 -> totalPoints += 7.0
            physicalActivityMinutes >= 15 -> totalPoints += 5.0
            physicalActivityMinutes > 0 -> totalPoints += 3.0
        }

        // 8. Skill Development (10 points) - based on duration
        when {
            skillDevelopmentMinutes >= 60 -> totalPoints += 10.0
            skillDevelopmentMinutes >= 30 -> totalPoints += 7.0
            skillDevelopmentMinutes >= 15 -> totalPoints += 5.0
            skillDevelopmentMinutes > 0 -> totalPoints += 3.0
            skillDevelopment.isNotBlank() -> totalPoints += 3.0
        }

        // 9. Class Recap (10 points)
        if (classRecap) totalPoints += 10.0

        // 10. Social Media (5 points) - less is more
        when (socialMediaUsage) {
            "minimal" -> totalPoints += 5.0
            "moderate" -> totalPoints += 3.0
            "excessive" -> totalPoints += 0.0
        }

        // 11. Self-Reflection (10 points)
        var reflectionPoints = 0.0
        if (reflection.isNotBlank()) reflectionPoints += 4.0
        if (proudOf.isNotBlank()) reflectionPoints += 2.0
        if (improveTomorrow.isNotBlank()) reflectionPoints += 2.0
        if (learnedToday.isNotBlank()) reflectionPoints += 2.0
        totalPoints += reflectionPoints

        // 12. Sleep on Time (10 points)
        if (sleepOnTime) totalPoints += 10.0

        return (totalPoints / maxPoints) * 100.0
    }
}
