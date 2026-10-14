package com.studentlife.tracker.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.studentlife.tracker.data.model.DailyActivity
import com.studentlife.tracker.data.model.ProgressAnalytics
import com.studentlife.tracker.data.model.Result
import com.studentlife.tracker.data.model.UserProfile
import com.studentlife.tracker.data.model.LifeStage
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

/**
 * Repository handling all daily activity tracking and progress analysis.
 */
class ActivityRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private val uid: String?
        get() = auth.currentUser?.uid

    private fun activitiesCollection(userId: String) =
        firestore.collection("users").document(userId).collection("activities")

    /**
     * Save or update today's activity record.
     */
    suspend fun saveDailyActivity(activity: DailyActivity): Result<Unit> {
        val userId = uid ?: return Result.Error("User not authenticated.")
        return try {
            val score = activity.calculateScore()
            val updatedActivity = activity.copy(
                userId = userId,
                dailyScore = score,
                isCompleted = true
            )
            activitiesCollection(userId)
                .document(activity.date)
                .set(updatedActivity)
                .await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to save activity.", e)
        }
    }

    /**
     * Get activity for today. Returns null if not yet recorded.
     */
    suspend fun getTodayActivity(userProfile: UserProfile): Result<DailyActivity?> {
        val userId = uid ?: return Result.Error("User not authenticated.")
        val todayStr = dateFormat.format(Date())
        return try {
            val doc = activitiesCollection(userId).document(todayStr).get().await()
            if (doc.exists()) {
                val activity = doc.toObject(DailyActivity::class.java)
                Result.Success(activity)
            } else {
                // Return an empty activity for today
                val dayNumber = calculateCurrentDay(userProfile.startDate)
                Result.Success(
                    DailyActivity(
                        userId = userId,
                        date = todayStr,
                        dayNumber = dayNumber
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to load today's activity.", e)
        }
    }

    /**
     * Get activity for a specific date.
     */
    suspend fun getActivityForDate(date: String): Result<DailyActivity?> {
        val userId = uid ?: return Result.Error("User not authenticated.")
        return try {
            val doc = activitiesCollection(userId).document(date).get().await()
            if (doc.exists()) {
                Result.Success(doc.toObject(DailyActivity::class.java))
            } else {
                Result.Success(null)
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to load activity.", e)
        }
    }

    /**
     * Get all activities for the 30-day cycle, ordered by date.
     */
    suspend fun getAllActivities(): Result<List<DailyActivity>> {
        val userId = uid ?: return Result.Error("User not authenticated.")
        return try {
            val snapshot = activitiesCollection(userId)
                .orderBy("date", Query.Direction.ASCENDING)
                .get()
                .await()
            val activities = snapshot.toObjects(DailyActivity::class.java)
            Result.Success(activities)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to load activities.", e)
        }
    }

    /**
     * Calculate the current day number in the 30-day cycle.
     * Day 1 = start date, Day 30 = end date.
     */
    fun calculateCurrentDay(startDate: Date?): Int {
        if (startDate == null) return 1
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        val start = Calendar.getInstance().apply {
            time = startDate
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        val diff = today.time - start.time
        val daysPassed = (diff / (1000 * 60 * 60 * 24)).toInt()
        return (daysPassed + 1).coerceIn(1, 30)
    }

    /**
     * Build the 30-day journey list (all days with status and score).
     */
    fun build30DayJourney(
        startDate: Date?,
        activities: List<DailyActivity>
    ): List<DayEntry> {
        val activityMap = activities.associateBy { it.date }
        val entries = mutableListOf<DayEntry>()
        val currentDay = calculateCurrentDay(startDate)

        for (i in 1..30) {
            val cal = Calendar.getInstance().apply {
                time = startDate ?: Date()
                add(Calendar.DAY_OF_YEAR, i - 1)
            }
            val dateStr = dateFormat.format(cal.time)
            val activity = activityMap[dateStr]

            val status = when {
                i < currentDay -> DayStatus.COMPLETED
                i == currentDay -> DayStatus.TODAY
                else -> DayStatus.UPCOMING
            }
            entries.add(
                DayEntry(
                    dayNumber = i,
                    date = dateStr,
                    score = activity?.dailyScore ?: 0.0,
                    isCompleted = activity?.isCompleted ?: false,
                    status = status
                )
            )
        }
        return entries
    }

    /**
     * Compute full progress analytics from all activity records.
     */
    fun computeAnalytics(activities: List<DailyActivity>, startDate: Date?): ProgressAnalytics {
        val completed = activities.filter { it.isCompleted }
        if (completed.isEmpty()) {
            return ProgressAnalytics(
                lifeStage = LifeStage.fromScore(0.0)
            )
        }

        val avgScore = completed.map { it.dailyScore }.average()

        // Category computations
        val academic = completed.map {
            var pts = 0.0
            if (it.universityAttended) pts += 40.0
            if (it.totalClasses > 0) pts += (it.classesAttended.toDouble() / it.totalClasses) * 40.0
            if (it.classRecap) pts += 20.0
            pts
        }.average()

        val physical = completed.map {
            when {
                it.physicalActivityMinutes >= 45 -> 100.0
                it.physicalActivityMinutes >= 30 -> 70.0
                it.physicalActivityMinutes >= 15 -> 50.0
                it.physicalActivityMinutes > 0 -> 30.0
                else -> 0.0
            }
        }.average()

        val skill = completed.map {
            when {
                it.skillDevelopmentMinutes >= 60 -> 100.0
                it.skillDevelopmentMinutes >= 30 -> 70.0
                it.skillDevelopmentMinutes >= 15 -> 50.0
                it.skillDevelopmentMinutes > 0 || it.skillDevelopment.isNotBlank() -> 30.0
                else -> 0.0
            }
        }.average()

        val social = completed.map {
            (it.meaningfulInteractions.toDouble() / 5.0) * 100.0
        }.average()

        val discipline = completed.map {
            var pts = 0.0
            if (it.wakeEarly) pts += 33.33
            if (it.personalRoutine) pts += 33.33
            if (it.sleepOnTime) pts += 33.34
            pts
        }.average()

        val sleep = completed.map { if (it.sleepOnTime) 100.0 else 0.0 }.average()

        val socialMedia = completed.map {
            when (it.socialMediaUsage) {
                "minimal" -> 100.0
                "moderate" -> 60.0
                "excessive" -> 0.0
                else -> 60.0
            }
        }.average()

        val reflection = completed.map {
            var pts = 0.0
            if (it.reflection.isNotBlank()) pts += 40.0
            if (it.proudOf.isNotBlank()) pts += 20.0
            if (it.improveTomorrow.isNotBlank()) pts += 20.0
            if (it.learnedToday.isNotBlank()) pts += 20.0
            pts
        }.average()

        val learning = completed.map {
            if (it.learningDone && it.dailyLearning.isNotBlank()) 100.0
            else if (it.dailyLearning.isNotBlank()) 50.0
            else 0.0
        }.average()

        val habitConsistency = (academic + physical + skill + discipline + learning) / 5.0

        // Calculate streak
        val streak = calculateStreak(completed, startDate)

        // Identify strong and weak areas
        val categoryMap = mapOf(
            "Academic Consistency" to academic,
            "Physical Activity" to physical,
            "Skill Development" to skill,
            "Social Interaction" to social,
            "Personal Discipline" to discipline,
            "Sleep Consistency" to sleep,
            "Social Media Control" to socialMedia,
            "Self-Reflection" to reflection,
            "Daily Learning" to learning
        )

        val sorted = categoryMap.entries.sortedByDescending { it.value }
        val strong = sorted.filter { it.value >= 65.0 }.map { it.key }
        val weak = sorted.filter { it.value < 50.0 }.map { it.key }

        return ProgressAnalytics(
            completedDays = completed.size,
            averageScore = avgScore,
            currentStreak = streak,
            academicConsistency = academic,
            physicalActivity = physical,
            skillDevelopment = skill,
            socialInteraction = social,
            personalDiscipline = discipline,
            sleepConsistency = sleep,
            socialMediaUsage = socialMedia,
            selfReflection = reflection,
            dailyLearning = learning,
            overallHabitConsistency = habitConsistency,
            strongAreas = strong,
            weakAreas = weak,
            lifeStage = LifeStage.fromScore(avgScore)
        )
    }

    private fun calculateStreak(completed: List<DailyActivity>, startDate: Date?): Int {
        if (completed.isEmpty()) return 0
        val sortedDates = completed.map { it.date }.sortedDescending()
        var streak = 0
        val cal = Calendar.getInstance()
        // Start from today, go backwards
        for (dateStr in sortedDates) {
            streak++
            // Simple consecutive day check could be enhanced
        }
        return minOf(streak, 7) // Simplified — counts completed days up to 7
    }
}

/**
 * Represents a single day entry in the 30-day journey.
 */
data class DayEntry(
    val dayNumber: Int,
    val date: String,
    val score: Double,
    val isCompleted: Boolean,
    val status: DayStatus
)

enum class DayStatus {
    COMPLETED, TODAY, UPCOMING
}
