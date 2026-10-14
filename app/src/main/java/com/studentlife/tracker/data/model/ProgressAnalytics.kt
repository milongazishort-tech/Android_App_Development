package com.studentlife.tracker.data.model

/**
 * Progress analytics computed from a user's 30-day activity data.
 */
data class ProgressAnalytics(
    val completedDays: Int = 0,
    val averageScore: Double = 0.0,
    val currentStreak: Int = 0,

    // Category averages (0.0 – 100.0)
    val academicConsistency: Double = 0.0,    // attendance + classes + recap
    val physicalActivity: Double = 0.0,
    val skillDevelopment: Double = 0.0,
    val socialInteraction: Double = 0.0,
    val personalDiscipline: Double = 0.0,     // routine + sleep + wake
    val sleepConsistency: Double = 0.0,
    val socialMediaUsage: Double = 0.0,       // inverted: lower usage = higher score
    val selfReflection: Double = 0.0,
    val dailyLearning: Double = 0.0,
    val overallHabitConsistency: Double = 0.0,

    // Identified areas
    val strongAreas: List<String> = emptyList(),
    val weakAreas: List<String> = emptyList(),

    val lifeStage: LifeStage = LifeStage(1, "Critical / Highly Unbalanced", "", 0, 20)
)
