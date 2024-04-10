package com.jonichidev.todo.feature.todo.presentation.todolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jonichidev.todo.R
import com.jonichidev.todo.common.presentation.components.TodoTopAppBar
import com.jonichidev.todo.common.presentation.ui.theme.TodoTheme
import com.jonichidev.todo.feature.todo.domain.model.Todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    onNavigateToTodoForm: () -> Unit,
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
            FloatingActionButton(onClick = { onNavigateToTodoForm() }) {
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
                LinearProgressIndicator()
            }
        } else {
            TodoListBody(
                todos = uiState.todos,
                onItemClick = onNavigateToTodoForm,
                onCompleted = viewModel::completeTask,
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
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    onCompleted: (Int, Boolean) -> Unit = { _, _ -> },
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
                            onCompleted = onCompleted,
                            modifier =
                            Modifier
                                .padding(dimensionResource(id = R.dimen.padding_small))
                                .clickable { onItemClick() },
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
    onCompleted: (Int, Boolean) -> Unit = { _, _ -> },
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(
                        bottom = dimensionResource(id = R.dimen.padding_small),
                        end = dimensionResource(id = R.dimen.padding_small),
                    ),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = todo.isCompleted,
                    onCheckedChange = { checked ->
                        onCompleted(todo.id, checked)
                    },
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = todo.title,
                    textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else null,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.width(50.dp))
                Text(text = todo.description, modifier = Modifier.weight(1f))
            }
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
                    Todo("Todo 1", "Description 1", false, 1),
                    Todo("Todo 2", "Description 2", false, 2),
                    Todo("Todo 3", "Description 3", true, 3),
                ),
            onItemClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview() {
    TodoTheme {
        TodoItem(todo = Todo("Sample Todo", "Description 1", false, 1))
    }
}
