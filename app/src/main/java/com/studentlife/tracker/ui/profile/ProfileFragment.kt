package com.studentlife.tracker.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.studentlife.tracker.data.model.Result
import com.studentlife.tracker.data.repository.AuthRepository
import com.studentlife.tracker.databinding.FragmentProfileBinding
import com.studentlife.tracker.ui.auth.LoginActivity
import com.studentlife.tracker.ui.settings.SettingsActivity
import kotlinx.coroutines.launch

/**
 * Screen 13: Profile Screen
 * Displays user identity, 3 summary metrics (Days Completed, Avg Score, Best Streak),
 * account menu list (Edit Profile, Goals, Reminders, Settings, Help, Logout), and version footer.
 */
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val authRepository = AuthRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserData()
        setupListeners()
    }

    private fun loadUserData() {
        viewLifecycleOwner.lifecycleScope.launch {
            when (val result = authRepository.getUserProfile()) {
                is Result.Success -> {
                    val profile = result.data
                    if (profile.name.isNotBlank()) {
                        binding.tvUserName.text = profile.name
                    }
                    if (profile.email.isNotBlank()) {
                        binding.tvUserEmail.text = profile.email
                    }
                }
                else -> {
                    // Fallback to default user if guest or mock
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }

        binding.menuEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        binding.menuMyGoals.setOnClickListener {
            Toast.makeText(requireContext(), "Goals: 4 active habits tracked", Toast.LENGTH_SHORT).show()
        }

        binding.menuReminders.setOnClickListener {
            Toast.makeText(requireContext(), "Reminders: Daily check-in at 8:00 PM", Toast.LENGTH_SHORT).show()
        }

        binding.menuSettings.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }

        binding.menuHelpSupport.setOnClickListener {
            Toast.makeText(requireContext(), "Student Life Tracker Support: help@studentlife.app", Toast.LENGTH_LONG).show()
        }

        binding.menuLogout.setOnClickListener {
            showLogoutConfirmation()
        }
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to log out of your account?")
            .setPositiveButton("Logout") { _, _ ->
                authRepository.logout()
                val intent = Intent(requireContext(), LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
