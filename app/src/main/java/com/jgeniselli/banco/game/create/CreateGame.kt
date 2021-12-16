package com.jgeniselli.banco.game.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jgeniselli.banco.R
import com.jgeniselli.banco.game.play.ui.theme.BancoImobiliarioTheme

@Composable
fun CreateGameLoader(
    viewModel: CreateGameViewModel
) {
    val players = viewModel.players.observeAsState(listOf())
    val isStartEnabled = viewModel.buttonIsEnabled.observeAsState(false)
    CreateGame(
        players = players.value,
        onAddPlayer = viewModel::onAddPlayer,
        onClickStartGame = viewModel::onClickStartGame,
        isStartButtonEnabled = isStartEnabled.value
    )
}

@Composable
fun CreateGame(
    players: List<String>,
    onAddPlayer: (String) -> Unit,
    isStartButtonEnabled: Boolean,
    onClickStartGame: () -> Unit
) {
    val showAddPlayerModal = remember { mutableStateOf(false) }

    Column {
        TopAppBar(
            title = { Text("New Game") },
            actions = { StartGameButton(onClickStartGame, isStartButtonEnabled) }
        )
        Box(modifier = Modifier.fillMaxSize()) {
            PlayerList(players)
            AddPlayerButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = { showAddPlayerModal.value = true }
            )
        }
        if (showAddPlayerModal.value) {
            AddPlayerDialog(
                onDismiss = { showAddPlayerModal.value = false },
                onAddPlayer = onAddPlayer
            )
        }
    }
}

@Composable
private fun StartGameButton(
    onClick: () -> Unit,
    isEnabled: Boolean
) {
    IconButton(onClick = onClick, enabled = isEnabled) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_done_24),
            contentDescription = "Start Game"
        )
    }
}

@Composable
private fun PlayerList(players: List<String>) {
    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        items(players) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = it
            )
            Divider()
        }
    }
}

@Composable
private fun AddPlayerButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        modifier = modifier.padding(16.dp),
        onClick = onClick,
        content = {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_add_24),
                contentDescription = "Add Player"
            )
        }
    )
}

@Composable
private fun AddPlayerDialog(
    onDismiss: () -> Unit,
    onAddPlayer: (String) -> Unit
) {
    val name = remember { mutableStateOf("") }
    Dialog(onDismissRequest = onDismiss) {
        AddPlayerDialogContent(
            value = name.value,
            onValueChange = { name.value = it },
            onDone = {
                onDismiss()
                onAddPlayer(name.value)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewComposableCreateGame() {
    BancoImobiliarioTheme {
        CreateGame(
            players = listOf("Johnny", "Emily", "Mark"),
            onAddPlayer = {},
            onClickStartGame = {},
            isStartButtonEnabled = true
        )
    }
}