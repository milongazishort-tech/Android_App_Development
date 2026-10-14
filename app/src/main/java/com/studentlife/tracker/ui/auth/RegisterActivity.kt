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
import com.studentlife.tracker.databinding.ActivityRegisterBinding
import com.studentlife.tracker.ui.main.MainActivity
import kotlinx.coroutines.launch

/**
 * Account registration screen designed exactly per Stitch Screen 4 specifications.
 */
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.tvLogin.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            handleRegister()
        }
    }

    private fun handleRegister() {
        val name = binding.etName.text?.toString()?.trim().orEmpty()
        val email = binding.etEmail.text?.toString()?.trim().orEmpty()
        val university = binding.etUniversity.text?.toString()?.trim().orEmpty()
        val department = binding.etDepartment.text?.toString()?.trim().orEmpty()
        val password = binding.etPassword.text?.toString()?.trim().orEmpty()
        val confirmPassword = binding.etConfirmPassword.text?.toString()?.trim().orEmpty()

        if (name.isEmpty()) {
            binding.tilName.error = getString(R.string.error_empty_name)
            return
        }
        binding.tilName.error = null

        if (email.isEmpty()) {
            binding.tilEmail.error = getString(R.string.error_empty_email)
            return
        }
        binding.tilEmail.error = null

        if (password.length < 6) {
            binding.tilPassword.error = getString(R.string.error_password_too_short)
            return
        }
        binding.tilPassword.error = null

        if (password != confirmPassword) {
            binding.tilConfirmPassword.error = getString(R.string.error_passwords_mismatch)
            return
        }
        binding.tilConfirmPassword.error = null

        setLoading(true)
        lifecycleScope.launch {
            when (val result = authRepository.register(name, email, password, university, department)) {
                is Result.Success -> {
                    setLoading(false)
                    Toast.makeText(this@RegisterActivity, "Account created successfully!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                    finish()
                }
                is Result.Error -> {
                    setLoading(false)
                    Toast.makeText(this@RegisterActivity, result.message, Toast.LENGTH_LONG).show()
                }
                is Result.Loading -> Unit
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.btnRegister.isEnabled = !loading
    }
}
