package com.jonichidev.todo.common.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jonichidev.todo.R
import com.jonichidev.todo.common.navigation.TodoDestinationArgs.TODO_ID_ARG
import com.jonichidev.todo.common.navigation.TodoNavDestination.TODO_FORM_ROUTE
import com.jonichidev.todo.common.navigation.TodoNavDestination.TODO_LIST_ROUTE
import com.jonichidev.todo.common.navigation.TodoScreens.TODO_FORM_SCREEN
import com.jonichidev.todo.feature.todo.presentation.todoform.TodoFormScreen
import com.jonichidev.todo.feature.todo.presentation.todolist.TodoListScreen

@Composable
fun TodoNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = TODO_LIST_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(route = TODO_LIST_ROUTE) {
            TodoListScreen(
                onNavigateToTodoForm = { todoId ->
                    navController.navigate("$TODO_FORM_SCREEN/$todoId")
                },
            )
        }
        composable(
            route = TODO_FORM_ROUTE,
            arguments = listOf(navArgument(TODO_ID_ARG) { type = NavType.IntType })
        ) {
            TodoFormScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}
