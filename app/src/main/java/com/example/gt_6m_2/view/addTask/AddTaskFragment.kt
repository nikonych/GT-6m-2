package com.example.gt_6m_2.view.addTask

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gt_6m_2.R
import com.example.gt_6m_2.databinding.FragmentAddTaskBinding
import com.example.gt_6m_2.enums.TaskType
import com.example.gt_6m_2.model.Task
import com.example.gt_6m_2.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private val viewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.etDate.setOnClickListener {


            // Get Current Date
            val c: Calendar = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = activity?.let { it1 ->
                DatePickerDialog(
                    it1,
                    OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
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
                OnTimeSetListener { view, hourOfDay, minute -> binding.etTime.setText("$hourOfDay:$minute") },
                mHour,
                mMinute,
                false
            )
            timePickerDialog.show()
        }

        binding.btnAdd.setOnClickListener {
            val type = binding.radioGroup.checkedRadioButtonId
            val button = binding.radioGroup.findViewById<RadioButton>(type)
            val id = binding.radioGroup.indexOfChild(button)
            var taskType: TaskType? = null
            when (id) {
                0 -> taskType = TaskType.TASK
                1 -> taskType = TaskType.EVENT
                2 -> taskType = TaskType.GOAL
            }

            with(binding) {
                viewModel.insertTask(
                    Task(
                        title = etTaskTitle.text.toString(),
                        description = etDescription.text.toString(),
                        endDate = etDate.text.toString(),
                        endTime = etTime.text.toString(),
                        taskType = taskType
                    )
                )
            }
            findNavController().navigateUp()
        }
    }

}