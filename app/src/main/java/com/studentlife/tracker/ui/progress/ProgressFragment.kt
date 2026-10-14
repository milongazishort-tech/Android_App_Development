package com.studentlife.tracker.ui.progress

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.studentlife.tracker.R
import com.studentlife.tracker.databinding.FragmentProgressBinding
import com.studentlife.tracker.ui.prediction.LifeStagePredictionActivity

/**
 * Screen 9: Progress Analytics screen displaying weekly/monthly/yearly trends,
 * smooth cubic bezier line chart, monthly summary, and category breakdowns.
 */
class ProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupChart()
        setupTabs()

        binding.btnViewLifeStage.setOnClickListener {
            startActivity(Intent(requireContext(), LifeStagePredictionActivity::class.java))
        }
    }

    private fun setupChart() {
        val chart = binding.lineChart
        val primaryColor = ContextCompat.getColor(requireContext(), R.color.primary)

        val entries = listOf(
            Entry(0f, 52f),
            Entry(1f, 58f),
            Entry(2f, 68f),
            Entry(3f, 76f)
        )

        val dataSet = LineDataSet(entries, "Progress").apply {
            mode = LineDataSet.Mode.CUBIC_BEZIER
            color = primaryColor
            lineWidth = 3f
            setDrawCircles(true)
            setCircleColor(primaryColor)
            circleHoleColor = Color.WHITE
            circleRadius = 5f
            circleHoleRadius = 2.5f
            setDrawValues(true)
            valueTextSize = 10f
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.text_primary)
            setDrawFilled(true)
            fillColor = primaryColor
            fillAlpha = 35
        }

        chart.apply {
            data = LineData(dataSet)
            description.isEnabled = false
            legend.isEnabled = false
            setTouchEnabled(false)
            setDrawGridBackground(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                setDrawAxisLine(false)
                setDrawLabels(false)
            }

            axisLeft.apply {
                axisMinimum = 0f
                axisMaximum = 100f
                setDrawAxisLine(false)
                gridColor = ContextCompat.getColor(requireContext(), R.color.border_subtle)
                textColor = ContextCompat.getColor(requireContext(), R.color.text_hint)
                textSize = 9f
            }

            axisRight.isEnabled = false
            animateY(800)
            invalidate()
        }
    }

    private fun setupTabs() {
        val tabs = listOf(binding.tabWeekly, binding.tabMonthly, binding.tabYearly)

        tabs.forEach { tab ->
            tab.setOnClickListener {
                tabs.forEach { t ->
                    t.setBackgroundResource(R.color.transparent)
                    t.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
                    t.elevation = 0f
                }
                tab.setBackgroundResource(R.drawable.bg_pill_button)
                tab.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                tab.elevation = 2f
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
