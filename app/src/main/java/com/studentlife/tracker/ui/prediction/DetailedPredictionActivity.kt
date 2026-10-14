package com.studentlife.tracker.ui.prediction

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.studentlife.tracker.databinding.ActivityDetailedPredictionBinding

/**
 * Screen 11 & 12: 30-Day Life Report / Detailed Prediction Activity
 * Displays celebratory trophy badge, 3 key metrics, 5 dimensions evaluation,
 * and 30-day velocity forecast with actionable key recommendations.
 */
class DetailedPredictionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedPredictionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedPredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Student Life Observation - 30-Day Life Report"
                )
                putExtra(
                    Intent.EXTRA_TEXT,
                    "I just completed my 30-Day Student Life Observation with a 76% score and Optimal status! 🚀"
                )
            }
            startActivity(Intent.createChooser(shareIntent, "Share Report"))
        }

        binding.btnViewFullReport.setOnClickListener {
            Toast.makeText(this, "Full 30-day report details loaded", Toast.LENGTH_SHORT).show()
        }

        binding.btnDownloadSummary.setOnClickListener {
            Toast.makeText(this, "Generating PDF summary report...", Toast.LENGTH_SHORT).show()
        }
    }
}
