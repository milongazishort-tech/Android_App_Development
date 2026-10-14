package com.studentlife.tracker.data.model

/**
 * Represents one of the Ten Life Progress Stages based on average score.
 */
data class LifeStage(
    val stageNumber: Int,
    val name: String,
    val description: String,
    val minScore: Int,
    val maxScore: Int
) {
    companion object {
        /**
         * The ten life stages as defined in the SRS.
         */
        val ALL_STAGES = listOf(
            LifeStage(1,  "Critical / Highly Unbalanced",    "Your lifestyle needs significant attention across all areas.", 0,  20),
            LifeStage(2,  "Very Low Progress",               "You are in early stages. Small steps will create momentum.", 21, 30),
            LifeStage(3,  "Low Progress",                    "Building awareness is your first step toward growth.", 31, 40),
            LifeStage(4,  "Needs Improvement",               "You have potential — focus on consistency.", 41, 50),
            LifeStage(5,  "Developing",                      "You are building healthy habits. Keep going.", 51, 60),
            LifeStage(6,  "Improving",                       "Your lifestyle is showing real progress.", 61, 70),
            LifeStage(7,  "Good Balance",                    "You maintain a healthy balance across most areas.", 71, 80),
            LifeStage(8,  "Strong Progress",                 "Your discipline and consistency are impressive.", 81, 88),
            LifeStage(9,  "Excellent Discipline",            "You demonstrate exceptional self-management.", 89, 95),
            LifeStage(10, "Exceptional Self-Development",    "You are at the highest tier of personal excellence.", 96, 100)
        )

        /**
         * Get the Life Stage for a given average score.
         */
        fun fromScore(averageScore: Double): LifeStage {
            val score = averageScore.toInt()
            return ALL_STAGES.firstOrNull { score in it.minScore..it.maxScore }
                ?: ALL_STAGES.last()
        }
    }
}
