package com.jonichidev.todo.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jonichidev.todo.R
import com.jonichidev.todo.feature.todo.presentation.todoform.TodoFormScreen
import com.jonichidev.todo.feature.todo.presentation.todolist.TodoListScreen


object TodoListDestination : TodoNavDestination {
    override val route = "todoList"
    override val titleRes = R.string.app_name
}

object TodoFormDestination : TodoNavDestination {
    override val route = "todoForm"
    override val titleRes = R.string.app_name
}

@Composable
fun TodoNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = TodoListDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(route = TodoListDestination.route) {
            TodoListScreen(
                onNavigateToTodoForm = { navController.navigate(TodoFormDestination.route) },
            )
        }
        composable(route = TodoFormDestination.route) {
            TodoFormScreen(navigateBack = { navController.popBackStack() })
        }
    }
}
