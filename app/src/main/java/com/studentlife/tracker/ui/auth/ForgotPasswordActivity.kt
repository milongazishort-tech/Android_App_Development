package com.studentlife.tracker.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.studentlife.tracker.R
import com.studentlife.tracker.data.model.Result
import com.studentlife.tracker.data.repository.AuthRepository
import com.studentlife.tracker.databinding.ActivityForgotPasswordBinding
import kotlinx.coroutines.launch

/**
 * Screen to request a password reset email.
 */
class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSendReset.setOnClickListener {
            val email = binding.etEmail.text?.toString()?.trim().orEmpty()
            if (email.isEmpty()) {
                binding.tilEmail.error = getString(R.string.error_empty_email)
                return@setOnClickListener
            }
            binding.tilEmail.error = null

            binding.progressBar.visibility = View.VISIBLE
            binding.btnSendReset.isEnabled = false

            lifecycleScope.launch {
                when (val result = authRepository.sendPasswordReset(email)) {
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSendReset.isEnabled = true
                        Toast.makeText(this@ForgotPasswordActivity, "Password reset link sent to your email", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSendReset.isEnabled = true
                        Toast.makeText(this@ForgotPasswordActivity, result.message, Toast.LENGTH_LONG).show()
                    }
                    is Result.Loading -> Unit
                }
            }
        }
    }
}
