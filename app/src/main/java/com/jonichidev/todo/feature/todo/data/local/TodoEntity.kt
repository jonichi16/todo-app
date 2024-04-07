package com.jonichidev.todo.feature.todo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class TodoEntity(
    val title: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
