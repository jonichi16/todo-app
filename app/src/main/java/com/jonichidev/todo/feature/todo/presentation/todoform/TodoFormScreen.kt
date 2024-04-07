package com.jonichidev.todo.feature.todo.presentation.todoform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.jonichidev.todo.R
import com.jonichidev.todo.common.presentation.components.InputField
import com.jonichidev.todo.common.presentation.components.TodoButton
import com.jonichidev.todo.common.presentation.components.TodoTopAppBar
import com.jonichidev.todo.feature.todo.presentation.navigation.TodoNavDestination
import kotlinx.coroutines.launch

object TodoFormDestination : TodoNavDestination {
    override val route = "TODO_FORM"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoFormScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TodoFormViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),
        topBar = {
            TodoTopAppBar(title = "Add Todo", canNavigateBack = true, navigateUp = navigateBack)
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            InputField(
                label = "Title",
                value = uiState.title,
                onValueChange = viewModel::updateTitle,
                modifier = Modifier,
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            InputField(
                label = "Description",
                value = uiState.description,
                onValueChange = viewModel::updateDescription,
                modifier = Modifier,
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            TodoButton(
                text = stringResource(id = R.string.add_todo_title),
                enabled = uiState.isFormValid,
                onClick = {
                    coroutineScope.launch {
                        viewModel.createTodo()
                        navigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
