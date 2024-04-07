package com.jonichidev.todo.feature.todo.data.repository

import com.jonichidev.todo.feature.todo.data.local.TodoDatabase
import com.jonichidev.todo.feature.todo.data.mapper.toTodo
import com.jonichidev.todo.feature.todo.data.mapper.toTodoEntity
import com.jonichidev.todo.feature.todo.domain.model.Todo
import com.jonichidev.todo.feature.todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepositoryImpl
    @Inject
    constructor(
        private val db: TodoDatabase,
    ) : TodoRepository {
        private val dao = db.dao

        override suspend fun createTodo(todo: Todo) {
            dao.upsert(todo.toTodoEntity())
        }

        override suspend fun updateTodo(todo: Todo) {
            dao.upsert(todo.toTodoEntity())
        }

        override suspend fun deleteTodo(todo: Todo) {
            dao.delete(todo.toTodoEntity())
        }

        override suspend fun completeTodo(
            id: Int,
            isCompleted: Boolean,
        ) {
            dao.updateCompleted(id, isCompleted)
        }

        override fun getTodoById(id: Int): Flow<Todo> {
            return dao.getById(id).map {
                it.toTodo()
            }
        }

        override fun getTodos(): Flow<List<Todo>> {
            return dao.getAll().map { todoEntities ->
                todoEntities.map { todo -> todo.toTodo() }
            }
        }
    }
