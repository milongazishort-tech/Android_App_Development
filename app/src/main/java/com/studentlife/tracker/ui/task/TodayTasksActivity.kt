package com.studentlife.tracker.ui.task

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.studentlife.tracker.R
import com.studentlife.tracker.databinding.ActivityTodayTasksBinding

/**
 * Screen 6: Today's Tasks screen displaying the 12 daily observation habits with category filters.
 */
class TodayTasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodayTasksBinding
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodayTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        setupRecyclerView()
        setupCategoryChips()
        updateProgressSummary()
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter {
            updateProgressSummary()
        }
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = adapter

        // Initial default 12 habits exactly matching Stitch Screen 6
        val initialTasks = listOf(
            TaskItem(1, "01", "Waking up early in the morning", "Health", true),
            TaskItem(2, "02", "Going University", "Study", true),
            TaskItem(3, "03", "How many classes to attend?", "Study", true),
            TaskItem(4, "04", "Observing religious principles", "Spiritual", true),
            TaskItem(5, "05", "Meeting up with a number of friends", "Social", true),
            TaskItem(6, "06", "What did we learn today?", "Learning", false),
            TaskItem(7, "07", "Sports OR Gym, OR hanging out.", "Fitness", false),
            TaskItem(8, "08", "Earning / Skill Development", "Career", true),
            TaskItem(9, "09", "Class Work Recap", "Study", false),
            TaskItem(10, "10", "Social Media OR News See", "Balance", true),
            TaskItem(11, "11", "Self-reflection", "Reflection", false),
            TaskItem(12, "12", "Sleep - quickly", "Rest", false)
        )
        adapter.submitList(initialTasks)
    }

    private fun updateProgressSummary() {
        val completed = adapter.getCompletedCount()
        val total = adapter.getTotalCount()
        val percent = if (total > 0) (completed * 100) / total else 0

        binding.tvCompletedRatio.text = "$completed of $total Completed"
        binding.tvProgressRatioPercent.text = "$percent%"
        binding.progressTasksBar.progress = percent
    }

    private fun setupCategoryChips() {
        val chips = listOf(
            binding.chipAll to "All",
            binding.chipStudy to "Study",
            binding.chipHealth to "Health",
            binding.chipCareer to "Career",
            binding.chipSocial to "Social",
            binding.chipRoutine to "Routine"
        )

        chips.forEach { (chipView, category) ->
            chipView.setOnClickListener {
                // Reset all chips styling
                chips.forEach { (cv, _) ->
                    cv.setBackgroundResource(R.drawable.bg_card_stitch)
                    cv.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))
                }
                // Highlight active chip
                chipView.setBackgroundResource(R.drawable.bg_pill_button)
                chipView.setTextColor(ContextCompat.getColor(this, R.color.white))

                adapter.filterByCategory(category)
            }
        }
    }
}
