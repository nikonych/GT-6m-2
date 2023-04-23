package com.example.gt_6m_2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gt_6m_2.model.Task
import com.example.gt_6m_2.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    fun getActiveTasks(): List<Task> {
        return repository.getActiveTasks()
    }

    fun getPassiveTasks(): List<Task> {
        return repository.getPassiveTasks()
    }

    fun insertTask(task: Task){
        repository.insertTask(task)
    }

    fun updateTask(task: Task){
        repository.updateTask(task)
    }

    fun deleteTask(task: Task){
        repository.deleteTask(task)
    }
}