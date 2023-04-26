package com.example.gt_6m_2.view.main.adapter

import android.content.Context
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.gt_6m_2.R
import com.example.gt_6m_2.databinding.ItemTaskBinding
import com.example.gt_6m_2.enums.TaskType
import com.example.gt_6m_2.model.Task
import com.example.gt_6m_2.view.changeTask.DialogTaskFragment
import com.example.gt_6m_2.view.main.TaskChangeListener
import com.example.gt_6m_2.viewmodel.TaskViewModel


class ActiveTaskAdapter(
    private var list: MutableList<Task>,
    private val viewModel: TaskViewModel,
    private val clickListener: TaskChangeListener,
    private val fragmentManager: FragmentManager
) : Adapter<ActiveTaskAdapter.ActiveTaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveTaskViewHolder {
        return ActiveTaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    fun updateList(tasks: List<Task>,) {
        list.clear()
        list.addAll(tasks)
        notifyDataSetChanged()

    }

    fun updateTask(task: Task, position: Int){
        list[position] = task
        notifyItemChanged(position)
    }

    fun deleteTask(position: Int){
        try {

            list.removeAt(position)
        } catch (e: Exception) {

        }
        notifyItemRemoved(position)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ActiveTaskViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    inner class ActiveTaskViewHolder(private val binding: ItemTaskBinding) :
        ViewHolder(binding.root) {
        fun bind(task: Task, position: Int) {
            with(binding) {
                tvTitle.text = task.title
                tvTime.text = task.endTime
                tvDate.text = task.endDate
                checkbox.isChecked = false
                when (task.taskType) {
                    TaskType.TASK -> imgType.setImageResource(R.drawable.task)
                    TaskType.GOAL -> imgType.setImageResource(R.drawable.goal)
                    TaskType.EVENT -> imgType.setImageResource(R.drawable.event)
                    else -> {
                        imgType.setImageResource(R.drawable.task)
                    }
                }

                checkbox.setOnClickListener {
                    if (checkbox.isChecked) {
                        task.isCompleted = true
                        viewModel.updateTask(task)
                        clickListener.onClick(task)
                    }
                }

                item.setOnLongClickListener {
                    DialogTaskFragment(task, this@ActiveTaskAdapter, position).show(fragmentManager, "gg")
                    return@setOnLongClickListener true
                }
            }
        }

    }
}
