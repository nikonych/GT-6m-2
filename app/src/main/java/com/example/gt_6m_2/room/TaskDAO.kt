package com.example.gt_6m_2.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverter
import androidx.room.Update
import com.example.gt_6m_2.model.Task
import java.sql.Time
import java.util.Date

@Dao
interface TaskDAO {

    @Insert
    fun insertTask(vararg task: Task)

    @Query("SELECT * FROM Task where isCompleted = 0 ORDER BY endDate")
    fun getActiveTasks() : List<Task>

    @Query("SELECT * FROM Task where isCompleted = 1 ORDER BY endDate")
    fun getPassiveTasks() : List<Task>

    @Update
    fun updateTask(vararg task: Task)

    @Delete
    fun deleteTask(vararg task: Task)

    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }


}