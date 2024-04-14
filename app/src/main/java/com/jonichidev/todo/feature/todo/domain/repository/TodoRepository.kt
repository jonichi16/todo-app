package com.jonichidev.todo.feature.todo.domain.repository

import com.jonichidev.todo.feature.todo.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun createTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun completeTodo(
        id: Int,
        isCompleted: Boolean,
    )

    suspend fun getTodoById(id: Int): Todo

    suspend fun getTodos(): List<Todo>

    fun getTodosStream(): Flow<List<Todo>>

    fun getTodoStream(id: Int): Flow<Todo>
}
