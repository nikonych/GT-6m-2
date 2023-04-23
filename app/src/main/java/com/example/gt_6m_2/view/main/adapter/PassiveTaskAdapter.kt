package com.example.gt_6m_2.view.main.adapter

import android.graphics.Color
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gt_6m_2.R
import com.example.gt_6m_2.databinding.ItemTaskBinding
import com.example.gt_6m_2.enums.TaskType
import com.example.gt_6m_2.model.Task
import com.example.gt_6m_2.view.main.TaskChangeListener
import com.example.gt_6m_2.viewmodel.TaskViewModel

class PassiveTaskAdapter(
    private val list: MutableList<Task>,
    private val viewModel: TaskViewModel,
    private val clickListener: TaskChangeListener
) : RecyclerView.Adapter<PassiveTaskAdapter.PassiveTaskViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassiveTaskViewHolder {
        return PassiveTaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun updateList(tasks: List<Task>) {
        list.clear()
        list.addAll(tasks)
        notifyDataSetChanged()

    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PassiveTaskViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    inner class PassiveTaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, position: Int) {
            with(binding) {
                val string = SpannableString(task.title)
                string.setSpan(StrikethroughSpan(), 0, string.length, 0)
                tvTitle.text = string
                tvTitle.setTextColor(Color.parseColor("#B1B1B1"))
                tvTime.setTextColor(Color.parseColor("#B1B1B1"))
                tvDate.setTextColor(Color.parseColor("#B1B1B1"))
                tvTime.text = task.endTime
                tvDate.text = task.endDate
                checkbox.isChecked = true
                when (task.taskType) {
                    TaskType.TASK -> imgType.setImageResource(R.drawable.task_passive)
                    TaskType.GOAL -> imgType.setImageResource(R.drawable.goal_passive)
                    TaskType.EVENT -> imgType.setImageResource(R.drawable.event_passive)
                    else -> {
                        imgType.setImageResource(R.drawable.task)
                    }
                }

                checkbox.setOnClickListener {
                    if (!checkbox.isChecked) {
                        task.isCompleted = false
                        viewModel.updateTask(task)
                        clickListener.onClick(task)
                    }
                }
            }
        }

    }

}