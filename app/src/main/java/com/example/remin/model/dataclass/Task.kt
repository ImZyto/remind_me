package com.example.remin.model.dataclass

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "tasks_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    var highPriority: Boolean = false,
    var description: String?,
    var date: Date,
    val localization: String = ""
) : Parcelable
