package com.jgeniselli.banco.newgame

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.ui.component.PlayerList
import com.jgeniselli.banco.ui.component.PlayerSummary
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewGameScreen(
    viewModel: NewGameViewModel = koinViewModel(),
    onStart: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState(NewGameUiState())
    var showAddPlayerDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isGameStarted) {
        if (uiState.isGameStarted) {
            onStart()
        }
    }

    NewGameContent(
        uiState = uiState,
        onAddPlayerClick = { showAddPlayerDialog = true },
        onStartClick = viewModel::onStartGame
    )
    if (showAddPlayerDialog) {
        AddPlayerDialog(
            onDone = {
                viewModel.onAddNewPlayer(it)
                showAddPlayerDialog = false
            },
            forbiddenNames = uiState.players.map { it.name },
            onCancel = { showAddPlayerDialog = false }
        )
    }
}

@Composable
fun NewGameContent(
    modifier: Modifier = Modifier,
    uiState: NewGameUiState,
    onAddPlayerClick: () -> Unit,
    onStartClick: () -> Unit,
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        PlayerList(players = uiState.players)

        Spacer(modifier = Modifier.weight(1f))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onAddPlayerClick,
                enabled = uiState.canAddNewPlayer
            ) {
                Text(text = "Add Player")
            }

            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                onClick = onStartClick,
                enabled = uiState.isGameStartAllowed
            ) {
                Text(text = "Start")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewNewGame() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        NewGameContent(
            modifier = Modifier,
            uiState = NewGameUiState(
                players = listOf(
                    PlayerSummary("John", Color.LightGray),
                    PlayerSummary("Emily", Color.LightGray),
                ),
                isGameStartAllowed = false,
                canAddNewPlayer = true
            ),
            onAddPlayerClick = {},
            onStartClick = {}
        )
    }
}