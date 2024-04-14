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

    @Query("UPDATE todo SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateCompleted(
        id: Int,
        isCompleted: Boolean,
    )

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getById(id: Int): TodoEntity

    @Query("SELECT * FROM todo")
    suspend fun getAll(): List<TodoEntity>

    @Query("SELECT * FROM todo")
    fun observeAll(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todo WHERE id = :todoId")
    fun observeById(todoId: Int): Flow<TodoEntity>
}
