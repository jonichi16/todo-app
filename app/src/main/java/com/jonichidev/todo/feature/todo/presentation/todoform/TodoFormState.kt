package com.jonichidev.todo.feature.todo.presentation.todoform

data class TodoFormState(
    val title: String = "",
    val isLoading: Boolean = false,
    val isFormValid: Boolean = false,
)
