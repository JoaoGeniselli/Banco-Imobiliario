package com.dosei.games.toybank.history.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.history.R
import com.dosei.games.toybank.history.data.model.HistoryEntry
import com.dosei.games.toybank.history.presentation.widget.HistoryRow
import com.dosei.games.toybank.ui.widget.BackButton
import java.util.Date

@Composable
internal fun HistoryScreen(
    controller: NavHostController,
    viewModel: HistoryViewModel,
) {
    val history by remember { viewModel.fetchHistory() }.collectAsState()

    HistoryContent(
        history = history,
        actions = HistoryActions(
            onBack = { controller.popBackStack() }
        )
    )
}

private data class HistoryActions(
    val onBack: () -> Unit = {},
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryContent(
    history: List<HistoryEntry>,
    actions: HistoryActions,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.history_feature_title)) },
                navigationIcon = { BackButton(onClick = actions.onBack) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (history.isNotEmpty()) {
                items(history) { HistoryRow(it) }
            } else {
                item {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = stringResource(R.string.history_empty_message)
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        HistoryContent(
            history = listOf(
                HistoryEntry.Deposit(25000, Date(), Player(name = "Oliver")),
                HistoryEntry.Withdraw(30000, Date(), Player(name = "Jeniffer")),
                HistoryEntry.Transfer(
                    amount = 45000,
                    date = Date(),
                    sourcePlayer = Player(name = "Robert"),
                    destinationPlayer = Player(name = "Lucy")
                ),
            ),
            actions = HistoryActions()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEmpty() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        HistoryContent(
            history = listOf(),
            actions = HistoryActions()
        )
    }
}
