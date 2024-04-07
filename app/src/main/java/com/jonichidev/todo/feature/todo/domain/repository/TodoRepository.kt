package com.jonichidev.todo.feature.todo.domain.repository

import com.jonichidev.todo.common.util.Conclusion
import com.jonichidev.todo.feature.todo.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun createTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    fun getTodoById(id: Int): Flow<Conclusion<Todo>>

    fun getTodos(): Flow<Conclusion<List<Todo>>>
}