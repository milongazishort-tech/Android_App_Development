package com.studentlife.tracker.ui.splash

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.studentlife.tracker.databinding.ActivitySplashBinding
import com.studentlife.tracker.ui.auth.LoginActivity
import com.studentlife.tracker.ui.main.MainActivity
import com.studentlife.tracker.ui.onboarding.OnboardingActivity
import com.studentlife.tracker.utils.PrefsManager

/**
 * Splash screen shown briefly on app launch.
 * Navigates to Onboarding, Login, or Main based on app and auth state.
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startPulseAnimation(binding.dot1, 0)
        startPulseAnimation(binding.dot2, 200)
        startPulseAnimation(binding.dot3, 400)

        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNextScreen()
        }, 1800)
    }

    private fun startPulseAnimation(view: View, startDelayMs: Long) {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.75f, 1.25f, 0.75f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.75f, 1.25f, 0.75f)
        val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0.4f, 1.0f, 0.4f)
        ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY, alpha).apply {
            duration = 1200
            repeatCount = ObjectAnimator.INFINITE
            startDelay = startDelayMs
            start()
        }
    }

    private fun navigateToNextScreen() {
        val onboardingDone = PrefsManager.isOnboardingCompleted(this)
        val intent = when {
            !onboardingDone -> Intent(this, OnboardingActivity::class.java)
            FirebaseAuth.getInstance().currentUser != null -> Intent(this, MainActivity::class.java)
            else -> Intent(this, LoginActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
