package com.jonichidev.todo.feature.todo.data.repository

import com.jonichidev.todo.common.util.Conclusion
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
class TodoRepositoryImpl @Inject constructor(
    private val db: TodoDatabase
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

    override fun getTodoById(id: Int): Flow<Conclusion<Todo>> {
        return dao.getById(id).map {
            Conclusion.Success(data = it.toTodo())
        }
    }

    override fun getTodos(): Flow<Conclusion<List<Todo>>> {
        return dao.getAll().map { todoEntities ->
            Conclusion.Success(data = todoEntities.map { todo -> todo.toTodo() })
        }
    }
}