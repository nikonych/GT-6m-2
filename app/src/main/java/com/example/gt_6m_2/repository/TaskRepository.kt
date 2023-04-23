package com.example.gt_6m_2.repository

import com.example.gt_6m_2.model.Task
import com.example.gt_6m_2.room.AppDatabase
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val appDatabase: AppDatabase
) {

    fun getActiveTasks(): List<Task> {
        return appDatabase.taskDAO().getActiveTasks()
    }

    fun getPassiveTasks(): List<Task> {
        return appDatabase.taskDAO().getPassiveTasks()
    }

    fun insertTask(task: Task) {
        appDatabase.taskDAO().insertTask(task)
    }

    fun updateTask(task: Task) {
        appDatabase.taskDAO().updateTask(task)
    }

    fun deleteTask(task: Task) {
        appDatabase.taskDAO().deleteTask(task)
    }

}