package com.jonichidev.todo.feature.todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TodoEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class TodoDatabase : RoomDatabase() {
    abstract val dao: TodoDao
}
