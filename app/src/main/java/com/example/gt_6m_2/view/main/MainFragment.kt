package com.example.gt_6m_2.view.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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




        if (listPassive.size + listActive.size <= 6) {
            activeAdapter =
                ActiveTaskAdapter(listActive.toMutableList(), viewModel, this, childFragmentManager)
            passiveAdapter = PassiveTaskAdapter(listPassive.toMutableList(), viewModel, this)

            binding.tvShowActiveTasks.visibility = View.GONE
            binding.tvShowPassiveTasks.visibility = View.GONE

            if (listPassive.isNotEmpty()) {
                binding.tvComplete.visibility = View.VISIBLE
            } else {
                binding.tvComplete.visibility = View.GONE
            }
        } else {
            if (listPassive.size == listActive.size) {
                activeAdapter =
                    ActiveTaskAdapter(
                        listActive.take(3).toMutableList(),
                        viewModel,
                        this,
                        childFragmentManager
                    )
                passiveAdapter =
                    PassiveTaskAdapter(listPassive.take(3).toMutableList(), viewModel, this)

                binding.tvShowActiveTasks.visibility = View.VISIBLE
                binding.tvShowPassiveTasks.visibility = View.VISIBLE
            } else if (listPassive.size < 3) {
                activeAdapter = ActiveTaskAdapter(
                    viewModel.getActiveTasks().take(6 - listPassive.size).toMutableList(),
                    viewModel,
                    this, childFragmentManager
                )
                passiveAdapter = PassiveTaskAdapter(listPassive.toMutableList(), viewModel, this)

                binding.tvShowPassiveTasks.visibility = View.GONE
                binding.tvShowActiveTasks.visibility = View.VISIBLE


                if (listPassive.isNotEmpty()) {
                    binding.tvComplete.visibility = View.VISIBLE
                } else {
                    binding.tvComplete.visibility = View.GONE
                }
            } else if (listActive.size < 3) {
                activeAdapter = ActiveTaskAdapter(
                    listActive.toMutableList(),
                    viewModel,
                    this,
                    childFragmentManager
                )
                passiveAdapter = PassiveTaskAdapter(
                    viewModel.getPassiveTasks().take(6 - listActive.size).toMutableList(),
                    viewModel,
                    this
                )

                binding.tvShowPassiveTasks.visibility = View.VISIBLE
                binding.tvShowActiveTasks.visibility = View.GONE
                binding.tvComplete.visibility = View.VISIBLE

            } else if (listPassive.isEmpty()) {
                activeAdapter =
                    ActiveTaskAdapter(
                        listActive.take(3).toMutableList(),
                        viewModel,
                        this,
                        childFragmentManager
                    )
                passiveAdapter = PassiveTaskAdapter(listPassive.toMutableList(), viewModel, this)
            } else {

                if (listPassive.size == 3) {
                    activeAdapter =
                        ActiveTaskAdapter(
                            listActive.take(3).toMutableList(),
                            viewModel,
                            this,
                            childFragmentManager
                        )
                    passiveAdapter =
                        PassiveTaskAdapter(listPassive.toMutableList(), viewModel, this)

                    binding.tvShowPassiveTasks.visibility = View.GONE
                    binding.tvShowActiveTasks.visibility = View.VISIBLE
                    binding.tvComplete.visibility = View.VISIBLE
                } else if (listActive.size == 3) {
                    activeAdapter = ActiveTaskAdapter(
                        listActive.toMutableList(),
                        viewModel,
                        this,
                        childFragmentManager
                    )
                    passiveAdapter =
                        PassiveTaskAdapter(listPassive.take(3).toMutableList(), viewModel, this)

                    binding.tvShowPassiveTasks.visibility = View.VISIBLE
                    binding.tvShowActiveTasks.visibility = View.GONE
                    binding.tvComplete.visibility = View.VISIBLE
                } else {
                    activeAdapter =
                        ActiveTaskAdapter(
                            listActive.take(3).toMutableList(),
                            viewModel,
                            this,
                            childFragmentManager
                        )
                    passiveAdapter =
                        PassiveTaskAdapter(listPassive.take(3).toMutableList(), viewModel, this)

                    binding.tvShowActiveTasks.visibility = View.GONE
                    binding.tvShowPassiveTasks.visibility = View.GONE
                    binding.tvComplete.visibility = View.VISIBLE

                }

            }
        }
        binding.rvActiveTasks.adapter = activeAdapter
        binding.rvPassiveTasks.adapter = passiveAdapter

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

    override fun onClick(item: Task) {
        val listActive = viewModel.getActiveTasks()
        val listPassive = viewModel.getPassiveTasks()
        with(binding) {
            if (listPassive.size + listActive.size <= 6) {
                activeAdapter.updateList(viewModel.getActiveTasks())
                passiveAdapter.updateList(viewModel.getPassiveTasks())

                tvShowActiveTasks.visibility = View.GONE
                tvShowPassiveTasks.visibility = View.GONE

                if (listPassive.isNotEmpty()) {
                    tvComplete.visibility = View.VISIBLE
                } else {
                    tvComplete.visibility = View.GONE
                }
            } else {
                if (listPassive.size == listActive.size) {
                    activeAdapter.updateList(viewModel.getActiveTasks().take(3))
                    passiveAdapter.updateList(viewModel.getPassiveTasks().take(3))

                    tvShowActiveTasks.visibility = View.VISIBLE
                    tvShowPassiveTasks.visibility = View.VISIBLE
                } else if (listPassive.size < 3) {
                    activeAdapter.updateList(viewModel.getActiveTasks().take(6 - listPassive.size))
                    passiveAdapter.updateList(viewModel.getPassiveTasks())

                    tvShowPassiveTasks.visibility = View.GONE
                    tvShowActiveTasks.visibility = View.VISIBLE


                    if (listPassive.isNotEmpty()) {
                        tvComplete.visibility = View.VISIBLE
                    } else {
                        tvComplete.visibility = View.GONE
                    }
                } else if (listActive.size < 3) {
                    activeAdapter.updateList(viewModel.getActiveTasks())
                    passiveAdapter.updateList(viewModel.getPassiveTasks().take(6 - listActive.size))

                    tvShowPassiveTasks.visibility = View.VISIBLE
                    tvShowActiveTasks.visibility = View.GONE
                    tvComplete.visibility = View.VISIBLE

                } else if (listPassive.isEmpty()) {
                    activeAdapter.updateList(viewModel.getActiveTasks().take(3))
                    passiveAdapter.updateList(viewModel.getPassiveTasks())
                } else {

                    if (listPassive.size == 3) {
                        activeAdapter.updateList(viewModel.getActiveTasks().take(3))
                        passiveAdapter.updateList(viewModel.getPassiveTasks())

                        tvShowPassiveTasks.visibility = View.GONE
                        tvShowActiveTasks.visibility = View.VISIBLE
                        tvComplete.visibility = View.VISIBLE
                    } else if (listActive.size == 3) {
                        activeAdapter.updateList(viewModel.getActiveTasks())
                        passiveAdapter.updateList(viewModel.getPassiveTasks().take(3))

                        tvShowPassiveTasks.visibility = View.VISIBLE
                        tvShowActiveTasks.visibility = View.GONE
                        tvComplete.visibility = View.VISIBLE
                    } else {
                        activeAdapter.updateList(viewModel.getActiveTasks().take(3))
                        passiveAdapter.updateList(viewModel.getPassiveTasks().take(3))

                        tvShowActiveTasks.visibility = View.GONE
                        tvShowPassiveTasks.visibility = View.GONE
                        tvComplete.visibility = View.VISIBLE

                    }

                }
            }

        }


    }


}