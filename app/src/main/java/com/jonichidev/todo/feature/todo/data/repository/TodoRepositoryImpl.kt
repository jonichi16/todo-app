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

        override suspend fun getTodoById(id: Int): Todo {
            return dao.getById(id).toTodo()
        }

        override suspend fun getTodos(): List<Todo> {
            return dao.getAll().map { it.toTodo() }
        }

        override fun getTodosStream(): Flow<List<Todo>> {
            return dao.observeAll().map {todoEntities ->
                todoEntities.map {todoEntity ->
                    todoEntity.toTodo()
                }
            }
        }

        override fun getTodoStream(id: Int): Flow<Todo> {
            return dao.observeById(id).map { todoEntity -> todoEntity.toTodo() }
        }
}
