package com.jonichidev.todo.common.navigation

import com.jonichidev.todo.common.navigation.TodoDestinationArgs.TODO_ID_ARG
import com.jonichidev.todo.common.navigation.TodoScreens.TODO_FORM_SCREEN
import com.jonichidev.todo.common.navigation.TodoScreens.TODO_LIST_SCREEN

object TodoScreens {
    const val TODO_LIST_SCREEN = "todoList"
    const val TODO_FORM_SCREEN = "todoForm"
}

object TodoDestinationArgs {
    const val TODO_ID_ARG = "todoId"
}

object TodoNavDestination {
    const val TODO_LIST_ROUTE = TODO_LIST_SCREEN
    const val TODO_FORM_ROUTE = "$TODO_FORM_SCREEN/{$TODO_ID_ARG}"
}