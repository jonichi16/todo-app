package com.jonichidev.todo.feature.todo.presentation.todoform

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonichidev.todo.common.navigation.TodoDestinationArgs.TODO_ID_ARG
import com.jonichidev.todo.feature.todo.domain.model.Todo
import com.jonichidev.todo.feature.todo.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TodoFormState(
    val title: String = "",
    val description: String = "",
    val isCompleted: Boolean = false,
    val isLoading: Boolean = false,
    val isFormValid: Boolean = false,
)

@HiltViewModel
class TodoFormViewModel
    @Inject
    constructor(
        private val repository: TodoRepository,
        private val savedStateHandle: SavedStateHandle
    ) : ViewModel() {
        private val todoId: Int? = savedStateHandle[TODO_ID_ARG]

        private val _uiState = MutableStateFlow(TodoFormState())
        val uiState = _uiState.asStateFlow()

        init {
            if (todoId != 0) {
                loadTodo(todoId!!)
            }
        }

        fun updateTitle(newTitle: String) {
            _uiState.update {
                it.copy(title = newTitle, isFormValid = validateForm(_uiState.value))
            }
        }

        fun updateDescription(newDescription: String) {
            _uiState.update {
                it.copy(description = newDescription, isFormValid = validateForm(_uiState.value))
            }
        }

        fun createTodo() =
            viewModelScope.launch {
                if (todoId == null) {
                    if (validateForm(_uiState.value)) {
                        val newTodo =
                            Todo(
                                _uiState.value.title,
                                _uiState.value.description,
                                _uiState.value.isCompleted,
                            )
                        repository.createTodo(newTodo)
                    }
                } else {
                    val updatedTodo = Todo(
                        _uiState.value.title,
                        _uiState.value.description,
                        _uiState.value.isCompleted,
                        todoId.toInt()
                    )
                    repository.updateTodo(updatedTodo)
                }
            }

        private fun validateForm(uiState: TodoFormState = _uiState.value): Boolean {
            return with(uiState) {
                title.isNotBlank()
            }
        }

        private fun loadTodo(todoId: Int) {
            _uiState.update {
                it.copy(isLoading = true)
            }
            viewModelScope.launch {
                repository.getTodoById(todoId).let {todo ->
                    _uiState.update {
                        it.copy(
                            title = todo.title,
                            description = todo.description,
                            isCompleted = todo.isCompleted,
                            isLoading = false,
                            isFormValid = todo.title.isNotBlank()
                        )
                    }
                }
            }
        }
    }
