package com.example.remin.model.dataclass

data class Task(
    val name: String,
    val highPriority: Boolean = false,
    var description: String?,
    var date: String?,
    val localization: String?
)
