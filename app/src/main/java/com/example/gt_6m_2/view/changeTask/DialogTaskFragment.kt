package com.example.gt_6m_2.view.changeTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.gt_6m_2.R
import com.example.gt_6m_2.databinding.FragmentDialogTaskBinding
import com.example.gt_6m_2.enums.TaskType
import com.example.gt_6m_2.model.Task
import com.example.gt_6m_2.view.main.adapter.ActiveTaskAdapter
import com.example.gt_6m_2.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class DialogTaskFragment(
    private var task: Task,
    private val adapter: ActiveTaskAdapter,
    private val position: Int
) : DialogFragment() {

    private lateinit var binding: FragmentDialogTaskBinding
    private val viewModel: TaskViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogTaskBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        fillData()
    }

    private fun fillData() {

        binding.etDate.setOnClickListener {


            // Get Current Date
            val c: Calendar = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = activity?.let { it1 ->
                DatePickerDialog(
                    it1,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        binding.etDate.setText(
                            dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                        )
                    }, mYear, mMonth, mDay
                )
            }
            datePickerDialog?.show()
        }

        binding.etTime.setOnClickListener {
            // Get Current Time
            // Get Current Time
            val c = Calendar.getInstance()
            val mHour = c[Calendar.HOUR_OF_DAY]
            val mMinute = c[Calendar.MINUTE]

            // Launch Time Picker Dialog

            // Launch Time Picker Dialog
            val timePickerDialog = TimePickerDialog(
                activity,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    binding.etTime.setText(
                        "$hourOfDay:$minute"
                    )
                },
                mHour,
                mMinute,
                false
            )
            timePickerDialog.show()
        }


        with(binding) {
            etTaskTitle.setText(task.title)
            etDescription.setText(task.description)
            etDate.setText(task.endDate)
            etTime.setText(task.endTime)
            when (task.taskType) {
                TaskType.EVENT -> btnEvent.isChecked = true
                TaskType.GOAL -> btnGoal.isChecked = true
                TaskType.TASK -> btnTask.isChecked = true
                else -> {
                    btnEvent.isChecked = true
                }
            }

            btnAdd.setOnClickListener {
                val type = radioGroup.checkedRadioButtonId
                val button = radioGroup.findViewById<RadioButton>(type)
                val id = radioGroup.indexOfChild(button)
                var taskType: TaskType? = null
                when (id) {
                    0 -> taskType = TaskType.TASK
                    1 -> taskType = TaskType.EVENT
                    2 -> taskType = TaskType.GOAL
                }
                task.title = etTaskTitle.text.toString()
                task.description = etDescription.text.toString()
                task.endDate = etDate.text.toString()
                task.endTime = etTime.text.toString()
                task.taskType = taskType

                viewModel.updateTask(task)
                adapter.updateTask(task, position)
                dismiss()

            }

            btnDelete.setOnClickListener {
                viewModel.deleteTask(task)
                adapter.deleteTask(position)
                dismiss()
            }


        }
    }

}


