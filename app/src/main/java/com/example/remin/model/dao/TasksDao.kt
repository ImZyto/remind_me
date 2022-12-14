package com.example.remin.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.remin.model.dataclass.Task

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTask(task: Task)

    @Query("SELECT * FROM tasks_table ORDER BY date DESC")
    fun getAllTasks(): List<Task>
}