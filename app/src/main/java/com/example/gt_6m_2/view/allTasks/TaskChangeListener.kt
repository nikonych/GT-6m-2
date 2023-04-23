package com.example.gt_6m_2.view.allTasks

import com.example.gt_6m_2.model.Task

interface TaskChangeListener {
    fun onClick(item: Task, position: Int)
}