package com.example.remin.model.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    var highPriority: Boolean = false,
    var description: String?,
    var date: Date,
    val localization: String = "",
    val latitude: Number,
    val longitude: Number
)
