package com.jonichidev.todo.feature.todo.domain.model

data class Todo(
    val title: String = "",
    val description: String,
    val isCompleted: Boolean,
    val id: Int = 0,
)
