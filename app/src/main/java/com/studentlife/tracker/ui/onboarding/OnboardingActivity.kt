package com.studentlife.tracker.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.studentlife.tracker.databinding.ActivityOnboardingBinding
import com.studentlife.tracker.ui.auth.LoginActivity
import com.studentlife.tracker.utils.PrefsManager

/**
 * Onboarding screen introducing daily habit tracking and 30-day transformation sprint.
 */
class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetStarted.setOnClickListener {
            PrefsManager.setOnboardingCompleted(this, true)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
