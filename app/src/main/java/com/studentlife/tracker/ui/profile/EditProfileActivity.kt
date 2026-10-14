package com.studentlife.tracker.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.studentlife.tracker.data.model.Result
import com.studentlife.tracker.data.repository.AuthRepository
import com.studentlife.tracker.databinding.ActivityEditProfileBinding
import kotlinx.coroutines.launch

/**
 * Screen to edit student profile information (name, university, department).
 */
class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadCurrentProfile()
        setupListeners()
    }

    private fun loadCurrentProfile() {
        lifecycleScope.launch {
            when (val result = authRepository.getUserProfile()) {
                is Result.Success -> {
                    val profile = result.data
                    binding.etFullName.setText(profile.name)
                    binding.etUniversity.setText(profile.university)
                    binding.etDepartment.setText(profile.department)
                }
                else -> Unit
            }
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSaveProfile.setOnClickListener {
            val name = binding.etFullName.text?.toString()?.trim().orEmpty()
            val uni = binding.etUniversity.text?.toString()?.trim().orEmpty()
            val dept = binding.etDepartment.text?.toString()?.trim().orEmpty()

            if (name.isBlank()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val updates = mapOf(
                    "name" to name,
                    "university" to uni,
                    "department" to dept
                )
                when (authRepository.updateProfile(name, uni, dept)) {
                    is Result.Success -> {
                        Toast.makeText(this@EditProfileActivity, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    is Result.Error -> {
                        Toast.makeText(this@EditProfileActivity, "Failed to update profile", Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }
}
