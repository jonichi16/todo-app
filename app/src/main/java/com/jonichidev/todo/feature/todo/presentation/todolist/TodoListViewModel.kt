package com.jonichidev.todo.feature.todo.presentation.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonichidev.todo.common.util.Conclusion
import com.jonichidev.todo.feature.todo.domain.model.Todo
import com.jonichidev.todo.feature.todo.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class TodoListState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
)

@HiltViewModel
class TodoListViewModel
    @Inject
    constructor(
        private val repository: TodoRepository,
    ) : ViewModel() {
        private val _isLoading = MutableStateFlow(false)
        private val _todos =
            repository.getTodos()
                .map { Conclusion.Success(it) }
        val uiState =
            combine(_todos, _isLoading) { todos, isLoading ->
                TodoListState(
                    todos = todos.data,
                    isLoading = isLoading,
                )
            }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = TodoListState(isLoading = true),
                )
    }
