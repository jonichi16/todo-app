package com.jonichidev.todo.feature.todo.data.mapper

import com.jonichidev.todo.feature.todo.data.local.TodoEntity
import com.jonichidev.todo.feature.todo.domain.model.Todo

fun TodoEntity.toTodo(): Todo {
    return Todo(
        title = title,
        description = description,
        isCompleted = isCompleted,
        id = id,
    )
}

fun Todo.toTodoEntity(): TodoEntity {
    return TodoEntity(
        title = title,
        description = description,
        isCompleted = isCompleted,
        id = id,
    )
}
