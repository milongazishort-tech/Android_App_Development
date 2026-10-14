package com.studentlife.tracker.ui.prediction

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.studentlife.tracker.databinding.ActivityLifeStagePredictionBinding

/**
 * Screen 10: Life Stage & Prediction
 * Displays current life stage, milestone gauge (72%), 10-level progression stepper,
 * forecast metrics, and navigation to the 30-day life report.
 */
class LifeStagePredictionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLifeStagePredictionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLifeStagePredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnInfo.setOnClickListener {
            // Future info dialog / guidance
        }

        binding.btnViewDetailedPrediction.setOnClickListener {
            startActivity(Intent(this, DetailedPredictionActivity::class.java))
        }
    }
}
