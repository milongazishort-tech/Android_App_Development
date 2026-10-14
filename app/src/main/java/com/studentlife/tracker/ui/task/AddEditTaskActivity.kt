package com.studentlife.tracker.ui.task

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.studentlife.tracker.R
import com.studentlife.tracker.databinding.ActivityAddEditTaskBinding
import java.util.Calendar
import java.util.Locale

/**
 * Screen 8: Add Custom Task activity with category picker grid and reminder time selection.
 */
class AddEditTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditTaskBinding
    private var selectedCategory: String = "Study"
    private var selectedHour = 21
    private var selectedMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }
        binding.btnSaveTop.setOnClickListener { handleSave() }
        binding.btnSaveTask.setOnClickListener { handleSave() }

        setupCategoryGrid()
        setupReminderPicker()
    }

    private fun setupCategoryGrid() {
        val categories = listOf(
            Triple(binding.catStudy, binding.ivCatStudy, binding.tvCatStudy) to "Study",
            Triple(binding.catHealth, binding.ivCatHealth, binding.tvCatHealth) to "Health",
            Triple(binding.catSpiritual, binding.ivCatSpiritual, binding.tvCatSpiritual) to "Spiritual",
            Triple(binding.catSocial, binding.ivCatSocial, binding.tvCatSocial) to "Social",
            Triple(binding.catLearning, binding.ivCatLearning, binding.tvCatLearning) to "Learning",
            Triple(binding.catActivity, binding.ivCatActivity, binding.tvCatActivity) to "Activity",
            Triple(binding.catWork, binding.ivCatWork, binding.tvCatWork) to "Work",
            Triple(binding.catOther, binding.ivCatOther, binding.tvCatOther) to "Other"
        )

        fun updateSelectedCategory(chosen: String) {
            selectedCategory = chosen
            categories.forEach { (views, catName) ->
                val (card, icon, text) = views
                val isSelected = catName.equals(chosen, ignoreCase = true)

                if (isSelected) {
                    card.setBackgroundResource(R.drawable.bg_card_stitch)
                    card.backgroundTintList = ContextCompat.getColorStateList(this, R.color.primary)
                    icon.setColorFilter(ContextCompat.getColor(this, R.color.white))
                    text.setTextColor(ContextCompat.getColor(this, R.color.white))
                } else {
                    card.setBackgroundResource(R.drawable.bg_card_stitch)
                    card.backgroundTintList = ContextCompat.getColorStateList(this, R.color.white)
                    icon.clearColorFilter()
                    text.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))
                }
            }
        }

        categories.forEach { (views, catName) ->
            views.first.setOnClickListener {
                updateSelectedCategory(catName)
            }
        }

        updateSelectedCategory("Study")
    }

    private fun setupReminderPicker() {
        binding.btnReminderTime.setOnClickListener {
            TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    selectedHour = hourOfDay
                    selectedMinute = minute
                    val amPm = if (hourOfDay >= 12) "PM" else "AM"
                    val hour12 = if (hourOfDay % 12 == 0) 12 else hourOfDay % 12
                    binding.tvReminderTime.text = String.format(Locale.getDefault(), "%02d:%02d %s", hour12, minute, amPm)
                },
                selectedHour,
                selectedMinute,
                false
            ).show()
        }
    }

    private fun handleSave() {
        val title = binding.etTaskTitle.text?.toString()?.trim().orEmpty()
        if (title.isEmpty()) {
            binding.tilTaskTitle.error = "Please enter task title"
            return
        }
        binding.tilTaskTitle.error = null

        Toast.makeText(this, "Task '$title' ($selectedCategory) saved!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
