package com.jonichidev.todo.common.presentation.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jonichidev.todo.common.presentation.ui.theme.TodoTheme

@Composable
fun LoadingIndicator(
    strokeWidth: Dp = 5.dp
) {
    CircularProgressIndicator(
        modifier = Modifier.drawBehind {
            drawCircle(
                Color.Red,
                radius = size.width / 2 - strokeWidth.toPx() / 2,
                style = Stroke(strokeWidth.toPx())
            )
        },
        color = Color.LightGray,
        strokeWidth = strokeWidth
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingIndicatorPreview() {
    TodoTheme {
        LoadingIndicator()
    }
}