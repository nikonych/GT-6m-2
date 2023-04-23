package com.example.gt_6m_2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gt_6m_2.enums.TaskType

@Entity(tableName = "Task")
data class Task(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "endDate")
    var endDate: String? = null,

    @ColumnInfo(name = "endTime")
    var endTime: String? = null,

    @ColumnInfo(name = "isCompleted")
    var isCompleted: Boolean = false,

    @ColumnInfo(name = "taskType")
    var taskType: TaskType? = null
)
