package com.studentlife.tracker.ui.task

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.studentlife.tracker.R
import com.studentlife.tracker.databinding.ItemTodayTaskBinding

data class TaskItem(
    val id: Int,
    val number: String,
    val title: String,
    val category: String,
    var isCompleted: Boolean
)

class TaskAdapter(
    private val onTaskChanged: () -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var allTasks = listOf<TaskItem>()
    private var displayedTasks = listOf<TaskItem>()

    fun submitList(tasks: List<TaskItem>) {
        allTasks = tasks
        displayedTasks = tasks
        notifyDataSetChanged()
    }

    fun filterByCategory(category: String) {
        displayedTasks = if (category.equals("All", ignoreCase = true)) {
            allTasks
        } else {
            allTasks.filter { it.category.equals(category, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

    fun getCompletedCount(): Int = allTasks.count { it.isCompleted }
    fun getTotalCount(): Int = allTasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTodayTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(displayedTasks[position])
    }

    override fun getItemCount(): Int = displayedTasks.size

    inner class TaskViewHolder(
        private val binding: ItemTodayTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TaskItem) {
            binding.tvTaskNumber.text = item.number
            binding.tvTaskTitle.text = item.title
            binding.tvTaskCategory.text = item.category
            binding.cbTaskCompleted.isChecked = item.isCompleted

            updateStrikeThrough(item.isCompleted)

            binding.cbTaskCompleted.setOnCheckedChangeListener { _, isChecked ->
                item.isCompleted = isChecked
                updateStrikeThrough(isChecked)
                onTaskChanged()
            }

            binding.root.setOnClickListener {
                binding.cbTaskCompleted.toggle()
            }
        }

        private fun updateStrikeThrough(completed: Boolean) {
            if (completed) {
                binding.tvTaskTitle.paintFlags = binding.tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvTaskTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.text_hint))
            } else {
                binding.tvTaskTitle.paintFlags = binding.tvTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvTaskTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.text_primary))
            }
        }
    }
}
