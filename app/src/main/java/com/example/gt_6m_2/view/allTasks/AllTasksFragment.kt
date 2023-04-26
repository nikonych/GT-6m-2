package com.example.gt_6m_2.view.allTasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gt_6m_2.R
import com.example.gt_6m_2.databinding.FragmentAllTasksBinding
import com.example.gt_6m_2.model.Task
import com.example.gt_6m_2.view.allTasks.adapter.TaskAdapter
import com.example.gt_6m_2.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllTasksFragment : Fragment(), TaskChangeListener {

    private val viewModel: TaskViewModel by viewModels()
    private lateinit var binding: FragmentAllTasksBinding
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllTasksBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var type = arguments?.getBoolean("tasks_type")
        var list: List<Task>? = null
        list = if (type == true) {
            viewModel.getActiveTasks()
        } else {
            viewModel.getPassiveTasks()
        }
        if (type == null){
            type = true
        }
        adapter = TaskAdapter(list as MutableList<Task>, type, viewModel, this)
        binding.rvTasks.adapter = adapter

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }
    }

    override fun onClick(item: Task, position: Int) {
        viewModel.deleteTask(task = item)
        adapter.deleteTask(position)
    }

}