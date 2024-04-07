package com.jonichidev.todo.feature.todo.presentation.todolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jonichidev.todo.R
import com.jonichidev.todo.common.presentation.TodoTopAppBar
import com.jonichidev.todo.common.presentation.ui.theme.TodoTheme
import com.jonichidev.todo.feature.todo.domain.model.Todo
import com.jonichidev.todo.feature.todo.presentation.navigation.TodoNavDestination

object TodoListDestination : TodoNavDestination {
    override val route = "TODO_LIST"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    navigateToAddTodo: () -> Unit,
    navigateToTodoUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TodoListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TodoTopAppBar(title = "Todo", canNavigateBack = false, scrollBehavior = scrollBehavior)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToAddTodo() }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_todo_title),
                )
            }
        },
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Loading",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        } else {
            TodoListBody(
                todos = uiState.todos,
                onItemClick = navigateToTodoUpdate,
                modifier =
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            )
        }
    }
}

@Composable
fun TodoListBody(
    todos: List<Todo>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        if (todos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.no_todo_description),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        } else {
            LazyColumn(
                modifier =
                Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
            ) {
                items(items = todos, key = { it.id }) { item ->
                    Row {
                        TodoItem(
                            todo = item,
                            modifier =
                            Modifier
                                .padding(dimensionResource(id = R.dimen.padding_small))
                                .clickable { onItemClick(item.id) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = false, onCheckedChange = { !it })
            Spacer(modifier = Modifier.weight(1f))
            Text(text = todo.title)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListBodyPreview() {
    TodoTheme {
        TodoListBody(
            todos =
            listOf(
                Todo("Todo 1", 1),
                Todo("Todo 2", 2),
                Todo("Todo 3", 3),
            ),
            onItemClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview() {
    TodoTheme {
        TodoItem(todo = Todo("Sample Todo", 1))
    }
}
