package com.jonichidev.todo.feature.todo.domain.repository

import com.jonichidev.todo.feature.todo.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun createTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    fun getTodoById(id: Int): Flow<Todo>

    fun getTodos(): Flow<List<Todo>>
}
