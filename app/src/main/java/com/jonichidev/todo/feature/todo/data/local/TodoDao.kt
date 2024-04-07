package com.jonichidev.todo.feature.todo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Upsert
    suspend fun upsert(todoEntity: TodoEntity)

    @Delete
    suspend fun delete(todoEntity: TodoEntity)

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getById(id: Int): Flow<TodoEntity>

    @Query("SELECT * FROM todo")
    fun getAll(): Flow<List<TodoEntity>>
}
