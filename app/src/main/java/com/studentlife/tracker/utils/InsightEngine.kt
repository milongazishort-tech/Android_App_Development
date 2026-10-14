package com.studentlife.tracker.utils

import com.studentlife.tracker.data.model.ProgressAnalytics

/**
 * Rule-based lifestyle insight generator.
 * Produces personalized observations and improvement suggestions
 * based on actual user data. NOT predictive — data-based only.
 */
object InsightEngine {

    /**
     * Generate personalized lifestyle insight paragraphs.
     */
    fun generateInsight(analytics: ProgressAnalytics): String {
        val sb = StringBuilder()
        sb.appendLine("Based on your 30-day journey data, here is your personalized lifestyle insight:\n")

        // Academic
        when {
            analytics.academicConsistency >= 80 ->
                sb.appendLine("📚 Your academic consistency is one of your strongest areas. Your attendance, class participation, and study habits reflect genuine dedication to your education.")
            analytics.academicConsistency >= 60 ->
                sb.appendLine("📚 Your academic performance shows a reasonable level of consistency. There are opportunities to strengthen your attendance and study routines further.")
            else ->
                sb.appendLine("📚 Your academic consistency has been relatively low during this period. Prioritizing university attendance and class preparation can significantly improve your overall progress.")
        }

        // Physical Activity
        when {
            analytics.physicalActivity >= 70 ->
                sb.appendLine("\n🏃 You have been consistently active physically. Regular exercise is positively contributing to your overall lifestyle balance.")
            analytics.physicalActivity >= 40 ->
                sb.appendLine("\n🏃 Your physical activity is moderate. Increasing the frequency or duration of exercise may further enhance your energy and focus.")
            else ->
                sb.appendLine("\n🏃 Your physical activity has been relatively low during this period. Adding regular walking, exercise, or outdoor activity may improve your overall lifestyle balance.")
        }

        // Skill Development
        when {
            analytics.skillDevelopment >= 70 ->
                sb.appendLine("\n💡 You are consistently investing time in learning and skill development. This is an excellent long-term habit.")
            analytics.skillDevelopment >= 40 ->
                sb.appendLine("\n💡 You are making some effort toward skill development. Dedicating consistent daily time to a useful skill can compound your growth.")
            else ->
                sb.appendLine("\n💡 Your recorded skill development time has been minimal. Consistently spending time on a useful skill — even 30 minutes a day — can lead to significant progress over time.")
        }

        // Social Media
        when {
            analytics.socialMediaUsage >= 80 ->
                sb.appendLine("\n📱 You have maintained good control over your social media and news usage during this period.")
            analytics.socialMediaUsage >= 50 ->
                sb.appendLine("\n📱 Your social media usage is moderate. Being more intentional about your screen time can free up energy for more meaningful activities.")
            else ->
                sb.appendLine("\n📱 Your recorded social media and news usage appears to be taking a significant portion of your daily routine. Reducing unnecessary screen time may improve your focus and productivity.")
        }

        // Sleep
        when {
            analytics.sleepConsistency >= 80 ->
                sb.appendLine("\n🌙 Your sleep consistency is excellent. Sleeping and waking at consistent times greatly benefits your mental clarity and daily performance.")
            analytics.sleepConsistency >= 50 ->
                sb.appendLine("\n🌙 Your sleep schedule is somewhat consistent. Working toward a regular sleep time can further improve your daily energy levels.")
            else ->
                sb.appendLine("\n🌙 Your sleep consistency has been irregular during this period. Establishing a consistent sleep schedule is one of the highest-impact habits you can build.")
        }

        // Self-Reflection
        when {
            analytics.selfReflection >= 70 ->
                sb.appendLine("\n🔍 Your self-reflection practice is strong. Regular journaling and self-awareness are key drivers of personal growth.")
            analytics.selfReflection >= 40 ->
                sb.appendLine("\n🔍 You are practicing some self-reflection. Deepening this habit can significantly accelerate your self-understanding.")
            else ->
                sb.appendLine("\n🔍 Your self-reflection entries have been sparse. Taking even 5 minutes daily to reflect on your progress can meaningfully shift your self-awareness.")
        }

        // Overall
        sb.appendLine("\n━━━━━━━━━━━━━━━━━━━━━━━━━")
        sb.appendLine("Overall: Your average daily score of ${String.format("%.1f", analytics.averageScore)}% places you at Life Stage ${analytics.lifeStage.stageNumber} — ${analytics.lifeStage.name}.")
        sb.appendLine("\nRemember: this insight is based solely on your own data. It is a mirror of your journey, not a judgment of your worth.")

        return sb.toString()
    }

    /**
     * Generate specific improvement suggestions for weak areas.
     */
    fun generateSuggestions(analytics: ProgressAnalytics): List<Suggestion> {
        val suggestions = mutableListOf<Suggestion>()

        if (analytics.physicalActivity < 50) {
            suggestions.add(
                Suggestion(
                    category = "Physical Activity",
                    icon = "🏃",
                    title = "Add Regular Exercise",
                    detail = "Add regular walking, exercise, or outdoor activity to your daily routine. Even 20–30 minutes daily can significantly improve your energy, focus, and overall well-being."
                )
            )
        }

        if (analytics.skillDevelopment < 50) {
            suggestions.add(
                Suggestion(
                    category = "Skill Development",
                    icon = "💡",
                    title = "Invest in Learning",
                    detail = "Spend consistent time developing a useful skill — programming, a language, freelancing, or creative work. Start with just 30 minutes daily and build up."
                )
            )
        }

        if (analytics.socialMediaUsage < 50) {
            suggestions.add(
                Suggestion(
                    category = "Social Media",
                    icon = "📱",
                    title = "Reduce Screen Time",
                    detail = "Reduce unnecessary social media and news consumption. Consider setting a daily limit and replacing that time with learning or physical activity."
                )
            )
        }

        if (analytics.sleepConsistency < 60) {
            suggestions.add(
                Suggestion(
                    category = "Sleep",
                    icon = "🌙",
                    title = "Establish a Sleep Schedule",
                    detail = "Set a consistent target sleep time and stick to it. Quality sleep is the foundation of every other positive habit in your lifestyle."
                )
            )
        }

        if (analytics.academicConsistency < 60) {
            suggestions.add(
                Suggestion(
                    category = "Academic",
                    icon = "📚",
                    title = "Strengthen Academic Consistency",
                    detail = "Prioritize university attendance and class participation. Review your class notes at the end of each day to reinforce learning."
                )
            )
        }

        if (analytics.selfReflection < 50) {
            suggestions.add(
                Suggestion(
                    category = "Self-Reflection",
                    icon = "🔍",
                    title = "Practice Daily Journaling",
                    detail = "Take 5–10 minutes every evening to reflect on your day. Write what you accomplished, what you learned, and what you want to improve."
                )
            )
        }

        if (analytics.personalDiscipline < 60) {
            suggestions.add(
                Suggestion(
                    category = "Personal Discipline",
                    icon = "⏰",
                    title = "Build Morning & Night Routines",
                    detail = "Waking early and completing your personal routine sets the tone for the entire day. Sleep at a fixed time and wake with a clear plan."
                )
            )
        }

        // If already strong in an area, add an affirmation
        for (strongArea in analytics.strongAreas.take(2)) {
            suggestions.add(
                Suggestion(
                    category = strongArea,
                    icon = "✅",
                    title = "Maintain $strongArea",
                    detail = "You are performing well in $strongArea. Keep this consistency going — it is one of your strongest habits."
                )
            )
        }

        return suggestions
    }
}

data class Suggestion(
    val category: String,
    val icon: String,
    val title: String,
    val detail: String
)
