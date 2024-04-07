package com.jonichidev.todo.feature.todo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jonichidev.todo.feature.todo.presentation.todoform.TodoFormDestination
import com.jonichidev.todo.feature.todo.presentation.todoform.TodoFormScreen
import com.jonichidev.todo.feature.todo.presentation.todolist.TodoListDestination
import com.jonichidev.todo.feature.todo.presentation.todolist.TodoListScreen

@Composable
fun TodoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = TodoListDestination.route,
        modifier = modifier,
    ) {
        composable(route = TodoListDestination.route) {
            TodoListScreen(
                navigateToAddTodo = { navController.navigate(TodoFormDestination.route) },
                navigateToTodoUpdate = {},
            )
        }
        composable(route = TodoFormDestination.route) {
            TodoFormScreen(navigateBack = { navController.popBackStack() })
        }
    }
}
