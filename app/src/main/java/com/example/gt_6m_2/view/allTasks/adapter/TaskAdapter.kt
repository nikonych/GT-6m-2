package com.example.gt_6m_2.view.allTasks.adapter

import android.graphics.Color
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gt_6m_2.R
import com.example.gt_6m_2.databinding.ItemFullTaskBinding
import com.example.gt_6m_2.enums.TaskType
import com.example.gt_6m_2.model.Task
import com.example.gt_6m_2.view.allTasks.TaskChangeListener
import com.example.gt_6m_2.viewmodel.TaskViewModel

class TaskAdapter(
    private val list: MutableList<Task>, private val task_type: Boolean,
    private val viewModel: TaskViewModel,
    private val clickListener: TaskChangeListener,

    ) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemFullTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    fun deleteTask(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class TaskViewHolder(private var binding: ItemFullTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, position: Int) {
            with(binding) {

                if (task_type) {
                    tvTitle.text = task.title
                    tvDescription.text = task.description
                    tvDate.text = task.endDate
                    tvTime.text = task.endTime
                    when (task.taskType) {
                        TaskType.TASK -> imgType.setImageResource(R.drawable.task)
                        TaskType.GOAL -> imgType.setImageResource(R.drawable.goal)
                        TaskType.EVENT -> imgType.setImageResource(R.drawable.event)
                        else -> {
                            imgType.setImageResource(R.drawable.task)
                        }
                    }
                } else {
                    val string = SpannableString(task.title)
                    string.setSpan(StrikethroughSpan(), 0, string.length, 0)
                    tvTitle.text = string
                    tvTitle.setTextColor(Color.parseColor("#B1B1B1"))
                    tvTime.setTextColor(Color.parseColor("#B1B1B1"))
                    tvDate.setTextColor(Color.parseColor("#B1B1B1"))
                    tvTime.text = task.endTime
                    tvDate.text = task.endDate
                    when (task.taskType) {
                        TaskType.TASK -> imgType.setImageResource(R.drawable.task_passive)
                        TaskType.GOAL -> imgType.setImageResource(R.drawable.goal_passive)
                        TaskType.EVENT -> imgType.setImageResource(R.drawable.event_passive)
                        else -> {
                            imgType.setImageResource(R.drawable.task)
                        }
                    }
                }
            }

            binding.btnDelete.setOnClickListener {
                viewModel.deleteTask(task)
                clickListener.onClick(task, position)
            }

        }
    }

}
