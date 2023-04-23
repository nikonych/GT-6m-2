package com.example.gt_6m_2.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gt_6m_2.model.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDAO(): TaskDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabaseClient(context: Context): AppDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, AppDatabase::class.java, "db")
                    .allowMainThreadQueries()
                    .build()

                return INSTANCE!!

            }
        }

    }
}