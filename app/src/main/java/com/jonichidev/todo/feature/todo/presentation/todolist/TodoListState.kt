package com.jonichidev.todo.feature.todo.presentation.todolist

import com.jonichidev.todo.feature.todo.domain.model.Todo

data class TodoListState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
)
