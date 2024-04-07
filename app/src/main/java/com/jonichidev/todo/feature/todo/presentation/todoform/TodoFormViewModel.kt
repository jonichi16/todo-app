package com.jonichidev.todo.feature.todo.presentation.todoform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(TodoFormState())
        val uiState = _uiState.asStateFlow()

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
                if (validateForm(_uiState.value)) {
                    val newTodo =
                        Todo(
                            _uiState.value.title,
                            _uiState.value.description,
                            _uiState.value.isCompleted,
                        )
                    repository.createTodo(newTodo)
                }
            }

        private fun validateForm(uiState: TodoFormState = _uiState.value): Boolean {
            return with(uiState) {
                title.isNotBlank()
            }
        }
    }
