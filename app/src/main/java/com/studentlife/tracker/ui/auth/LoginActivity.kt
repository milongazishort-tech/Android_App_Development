package com.studentlife.tracker.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.studentlife.tracker.R
import com.studentlife.tracker.data.model.Result
import com.studentlife.tracker.data.repository.AuthRepository
import com.studentlife.tracker.databinding.ActivityLoginBinding
import com.studentlife.tracker.ui.main.MainActivity
import kotlinx.coroutines.launch

/**
 * Login screen designed exactly per Stitch Screen 3 specifications.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            handleLogin()
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        // Social auth placeholders
        binding.btnGoogleLogin.setOnClickListener {
            Toast.makeText(this, "Google Sign-In", Toast.LENGTH_SHORT).show()
        }
        binding.btnFacebookLogin.setOnClickListener {
            Toast.makeText(this, "Facebook Sign-In", Toast.LENGTH_SHORT).show()
        }
        binding.btnAppleLogin.setOnClickListener {
            Toast.makeText(this, "Apple Sign-In", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleLogin() {
        val email = binding.etEmail.text?.toString()?.trim().orEmpty()
        val password = binding.etPassword.text?.toString()?.trim().orEmpty()

        if (email.isEmpty()) {
            binding.tilEmail.error = getString(R.string.error_empty_email)
            return
        }
        binding.tilEmail.error = null

        if (password.isEmpty()) {
            binding.tilPassword.error = getString(R.string.error_empty_password)
            return
        }
        binding.tilPassword.error = null

        setLoading(true)
        lifecycleScope.launch {
            when (val result = authRepository.login(email, password)) {
                is Result.Success -> {
                    setLoading(false)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
                is Result.Error -> {
                    setLoading(false)
                    Toast.makeText(this@LoginActivity, result.message, Toast.LENGTH_LONG).show()
                }
                is Result.Loading -> Unit
            }
        }
    }

    private fun handleForgotPassword() {
        val email = binding.etEmail.text?.toString()?.trim().orEmpty()
        if (email.isEmpty()) {
            binding.tilEmail.error = getString(R.string.error_empty_email)
            return
        }

        setLoading(true)
        lifecycleScope.launch {
            when (val result = authRepository.sendPasswordReset(email)) {
                is Result.Success -> {
                    setLoading(false)
                    Toast.makeText(this@LoginActivity, "Password reset email sent!", Toast.LENGTH_LONG).show()
                }
                is Result.Error -> {
                    setLoading(false)
                    Toast.makeText(this@LoginActivity, result.message, Toast.LENGTH_LONG).show()
                }
                is Result.Loading -> Unit
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !loading
    }
}
