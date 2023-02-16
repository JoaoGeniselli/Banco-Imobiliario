package com.jgeniselli.banco.game.history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.ui.component.TitleSubtitleRow
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = koinViewModel()
) {
    val history by viewModel.history.collectAsState(emptyList())
    HistoryContent(
        modifier = modifier,
        operations = history
    )
}

@Composable
fun HistoryContent(
    modifier: Modifier = Modifier,
    operations: List<FormattedOperation>
) {
    LazyColumn(modifier) {
        items(operations) { operation ->
            TitleSubtitleRow(
                icon = operation.icon,
                title = operation.title,
                subtitle = operation.description
            )
            Divider(modifier = Modifier.padding(start = 56.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHistory() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        HistoryContent(
            modifier = Modifier,
            operations = listOf(
                FormattedOperation(
                    Icons.Default.SwapHoriz,
                    "$ 500.00",
                    "From: John to: Emily",
                ),
                FormattedOperation(
                    Icons.Default.Add,
                    "$ 500.00",
                    "John",
                ),
                FormattedOperation(
                    Icons.Default.Remove,
                    "$ 500.00",
                    "John",
                )
            )
        )
    }
}
