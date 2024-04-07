package com.jonichidev.todo.feature.todo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class TodoEntity(
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
