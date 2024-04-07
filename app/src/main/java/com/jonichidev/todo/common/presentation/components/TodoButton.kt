package com.jonichidev.todo.common.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jonichidev.todo.common.presentation.ui.theme.TodoTheme

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
        Text(text = text.uppercase())
    }
}

@Preview(showBackground = true)
@Composable
fun TodoButtonPreview() {
    TodoTheme {
        TodoButton(text = "Sample")
    }
}
