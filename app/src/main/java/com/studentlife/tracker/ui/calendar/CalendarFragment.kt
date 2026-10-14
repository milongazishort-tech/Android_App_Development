package com.studentlife.tracker.ui.calendar

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.studentlife.tracker.R
import com.studentlife.tracker.databinding.FragmentCalendarBinding
import com.studentlife.tracker.ui.task.TodayTasksActivity

/**
 * Screen 7: Calendar View screen displaying monthly calendar with observation status dots
 * and day summary card.
 */
class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CalendarDayAdapter
    private var selectedDay = 20

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCalendarGrid()

        binding.cardDaySummary.setOnClickListener {
            startActivity(Intent(requireContext(), TodayTasksActivity::class.java))
        }
    }

    private fun setupCalendarGrid() {
        adapter = CalendarDayAdapter { day ->
            selectedDay = day.dayNumber
            updateDaysList()
            binding.tvSelectedDateLabel.text = "May $selectedDay, 2025"
        }

        binding.rvCalendarGrid.layoutManager = GridLayoutManager(requireContext(), 7)
        binding.rvCalendarGrid.adapter = adapter

        updateDaysList()
    }

    private fun updateDaysList() {
        val daysList = mutableListOf<CalendarDay>()
        // May 2025 starts on Thursday (index 3), so 3 empty cells
        repeat(3) {
            daysList.add(CalendarDay(0))
        }

        for (i in 1..31) {
            val indicator = when (i) {
                7 -> R.color.secondary      // Green dot
                14 -> R.color.primary       // Blue dot
                22 -> R.color.primary       // Blue dot
                27 -> R.color.primary_light // Indigo dot
                29 -> R.color.tertiary      // Amber dot
                else -> null
            }
            daysList.add(CalendarDay(i, isSelected = (i == selectedDay), indicatorColorRes = indicator))
        }

        adapter.submitList(daysList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
