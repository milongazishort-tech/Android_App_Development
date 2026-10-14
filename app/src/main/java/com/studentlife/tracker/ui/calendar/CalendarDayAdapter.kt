package com.studentlife.tracker.ui.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.studentlife.tracker.R
import com.studentlife.tracker.databinding.ItemCalendarDayBinding

data class CalendarDay(
    val dayNumber: Int,
    val isSelected: Boolean = false,
    val indicatorColorRes: Int? = null
)

class CalendarDayAdapter(
    private val onDaySelected: (CalendarDay) -> Unit
) : RecyclerView.Adapter<CalendarDayAdapter.DayViewHolder>() {

    private var days = listOf<CalendarDay>()

    fun submitList(list: List<CalendarDay>) {
        days = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding = ItemCalendarDayBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(days[position])
    }

    override fun getItemCount(): Int = days.size

    inner class DayViewHolder(
        private val binding: ItemCalendarDayBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(day: CalendarDay) {
            val context = binding.root.context

            if (day.dayNumber <= 0) {
                binding.tvDayNumber.text = ""
                binding.dotIndicator.visibility = View.GONE
                binding.root.isClickable = false
                return
            }

            binding.tvDayNumber.text = day.dayNumber.toString()
            binding.root.isClickable = true

            if (day.isSelected) {
                binding.tvDayNumber.setBackgroundResource(R.drawable.bg_pill_button)
                binding.tvDayNumber.setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                binding.tvDayNumber.setBackgroundResource(R.color.transparent)
                binding.tvDayNumber.setTextColor(ContextCompat.getColor(context, R.color.text_primary))
            }

            if (day.indicatorColorRes != null && !day.isSelected) {
                binding.dotIndicator.visibility = View.VISIBLE
                binding.dotIndicator.backgroundTintList = ContextCompat.getColorStateList(context, day.indicatorColorRes)
            } else {
                binding.dotIndicator.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                onDaySelected(day)
            }
        }
    }
}
