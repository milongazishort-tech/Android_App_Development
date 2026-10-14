package com.studentlife.tracker.ui.settings

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.studentlife.tracker.databinding.ActivitySettingsBinding
import com.studentlife.tracker.ui.onboarding.OnboardingActivity
import java.util.Calendar

/**
 * Settings Screen for managing reminder schedules, notifications, and reviewing onboarding.
 */
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.switchDailyReminder.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "enabled" else "disabled"
            Toast.makeText(this, "Daily reminders $status", Toast.LENGTH_SHORT).show()
        }

        binding.rowReminderTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    val amPm = if (hourOfDay >= 12) "PM" else "AM"
                    val hour12 = when {
                        hourOfDay == 0 -> 12
                        hourOfDay > 12 -> hourOfDay - 12
                        else -> hourOfDay
                    }
                    val formatted = String.format("%02d:%02d %s", hour12, minute, amPm)
                    binding.tvReminderTime.text = formatted
                    Toast.makeText(this, "Reminder set to $formatted", Toast.LENGTH_SHORT).show()
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            ).show()
        }

        binding.rowReviewOnboarding.setOnClickListener {
            startActivity(Intent(this, OnboardingActivity::class.java))
        }
    }
}
