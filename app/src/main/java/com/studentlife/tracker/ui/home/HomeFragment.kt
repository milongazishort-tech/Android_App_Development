package com.studentlife.tracker.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.studentlife.tracker.data.model.Result
import com.studentlife.tracker.data.repository.AuthRepository
import com.studentlife.tracker.databinding.FragmentHomeBinding
import com.studentlife.tracker.ui.task.TodayTasksActivity
import kotlinx.coroutines.launch

/**
 * Home Dashboard screen displaying 30-Day challenge hero card, daily metric cards,
 * circular progress gauge, streak badges, and upcoming routines.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val authRepository = AuthRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
                    val firstName = profile.name.split(" ").firstOrNull() ?: profile.name
                    binding.tvUserName.text = "$firstName ☀️"
                }
                else -> Unit
            }
        }
    }

    private fun setupListeners() {
        val openTodayTasks = View.OnClickListener {
            startActivity(Intent(requireContext(), TodayTasksActivity::class.java))
        }

        binding.cardHeroProgress.setOnClickListener(openTodayTasks)
        binding.cardTodayProgress.setOnClickListener(openTodayTasks)
        binding.tvViewDetails.setOnClickListener(openTodayTasks)
        binding.tvAllTasksLink.setOnClickListener(openTodayTasks)
        binding.cardNextUp.setOnClickListener(openTodayTasks)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
