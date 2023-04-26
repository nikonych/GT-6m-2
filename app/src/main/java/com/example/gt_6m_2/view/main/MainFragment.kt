package com.example.gt_6m_2.view.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gt_6m_2.R
import com.example.gt_6m_2.databinding.FragmentMainBinding
import com.example.gt_6m_2.model.Task
import com.example.gt_6m_2.view.main.adapter.ActiveTaskAdapter
import com.example.gt_6m_2.view.main.adapter.PassiveTaskAdapter
import com.example.gt_6m_2.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@AndroidEntryPoint
class MainFragment : Fragment(), TaskChangeListener {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: TaskViewModel by viewModels()
    private lateinit var activeAdapter: ActiveTaskAdapter
    private lateinit var passiveAdapter: PassiveTaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
        val formatted = current.format(formatter)
        binding.tvToday.text = formatted

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.addTaskFragment)
        }


        val listActive = viewModel.getActiveTasks()
        val listPassive = viewModel.getPassiveTasks()

        activeAdapter = ActiveTaskAdapter(listActive.toMutableList(), viewModel, this, childFragmentManager)
        passiveAdapter = PassiveTaskAdapter(listPassive.toMutableList(), viewModel, this)

        binding.rvActiveTasks.adapter = activeAdapter
        binding.rvPassiveTasks.adapter = passiveAdapter

        updateLists()

        binding.tvShowActiveTasks.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("tasks_type", true)
            findNavController().navigate(R.id.allTasksFragment, bundle)
        }

        binding.tvShowPassiveTasks.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("tasks_type", false)
            findNavController().navigate(R.id.allTasksFragment, bundle)
        }


    }


    private fun updateLists() {
        val listActive = viewModel.getActiveTasks()
        val listPassive = viewModel.getPassiveTasks()
        with(binding)
        {
            if (listActive.isEmpty() && listPassive.isEmpty()) {
                tvComplete.isVisible = false
                tvShowActiveTasks.isVisible = false
                tvShowPassiveTasks.isVisible = false
                imgEmpty.isVisible = true
                imgEmpty.setAnimationFromUrl("https://assets2.lottiefiles.com/private_files/lf30_e3pteeho.json")

                activeAdapter.updateList(listActive)
                passiveAdapter.updateList(listPassive)
            } else {
                if (listActive.size <= 3) {
                    tvShowActiveTasks.isVisible = false
                    activeAdapter.updateList(listActive)
                }
                if (listPassive.size <= 3 && listPassive.isNotEmpty()) {
                    tvComplete.isVisible = true
                    tvShowPassiveTasks.isVisible = false
                    passiveAdapter.updateList(listPassive)
                } else if (listPassive.isEmpty()) {
                    tvComplete.isVisible = false
                    tvShowPassiveTasks.isVisible = false
                    passiveAdapter.updateList(listPassive)
                }
                if (listActive.size > 3) {
                    if (listPassive.size <= 3) {
                        activeAdapter.updateList(listActive.take(6 - listPassive.size))
                        tvShowActiveTasks.isVisible = listActive.size > 6 - listPassive.size
                    } else {
                        activeAdapter.updateList(listActive.take(3))
                        tvShowActiveTasks.isVisible = true
                    }
                }
                if (listPassive.size > 3) {
                    if (listActive.size <= 3) {
                        passiveAdapter.updateList(listPassive.take(6 - listActive.size))
                        tvShowPassiveTasks.isVisible = listPassive.size > 6 - listActive.size
                    } else {
                        passiveAdapter.updateList(listPassive.take(3))
                        tvShowPassiveTasks.isVisible = true
                    }
                }

            }

        }

    }


    override fun onClick(item: Task) {
        updateLists()
    }



}