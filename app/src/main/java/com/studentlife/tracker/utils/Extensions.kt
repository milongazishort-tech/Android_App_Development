package com.studentlife.tracker.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

/**
 * Extension functions used across the app.
 */

fun View.show() { visibility = View.VISIBLE }
fun View.hide() { visibility = View.GONE }
fun View.invisible() { visibility = View.INVISIBLE }

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.snack(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun Double.toPercent(): String = "${this.toInt()}%"

fun Double.toScoreString(): String = String.format("%.1f%%", this)

/**
 * Get a motivational message based on score.
 */
fun Double.getMotivationalMessage(): String {
    return when {
        this >= 90 -> "Outstanding! You're excelling in all areas. 🌟"
        this >= 80 -> "Excellent work! Your discipline is inspiring. 💪"
        this >= 70 -> "Great job! You're maintaining a solid balance. 👍"
        this >= 60 -> "Good effort! Keep building on your progress. 🚀"
        this >= 50 -> "You're developing well. Consistency is key. 🌱"
        this >= 40 -> "Keep going — every step forward counts. 🌄"
        this >= 30 -> "Today was a challenge. Tomorrow is a new start. 🌅"
        else -> "Don't give up — small actions create big changes. 💫"
    }
}

/**
 * Get stage color (for UI tinting based on life stage).
 */
fun Int.getStageColor(): Int {
    return when (this) {
        1 -> 0xFFEF5350.toInt()  // Red
        2 -> 0xFFE53935.toInt()  // Deep Red
        3 -> 0xFFF57C00.toInt()  // Deep Orange
        4 -> 0xFFFF9800.toInt()  // Orange
        5 -> 0xFFFDD835.toInt()  // Yellow
        6 -> 0xFF8BC34A.toInt()  // Light Green
        7 -> 0xFF4CAF50.toInt()  // Green
        8 -> 0xFF00BCD4.toInt()  // Cyan
        9 -> 0xFF2196F3.toInt()  // Blue
        10 -> 0xFF9C27B0.toInt() // Purple
        else -> 0xFF4CAF50.toInt()
    }
}
