package com.jonichidev.todo.feature.todo.presentation.todoform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jonichidev.todo.R
import com.jonichidev.todo.feature.todo.presentation.navigation.TodoNavDestination
import kotlinx.coroutines.launch

object TodoFormDestination : TodoNavDestination {
    override val route = "TODO_FORM"
    override val titleRes = R.string.app_name
}

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
            TodoButton(
                text = stringResource(id = R.string.add_todo_title),
                enabled = uiState.isFormValid,
                onClick = {
                    coroutineScope.launch {
                        viewModel.createTodo()
                        navigateBack()
                    }
                },
            )
        }
    }
}

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier,
    ) {
        Text(text = label, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = "Enter $label") },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
        )
    }
}

@Composable
fun TodoButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = { onClick() },
        enabled = enabled,
        modifier = modifier,
    ) {
        Text(text = text)
    }
}
