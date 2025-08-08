package com.dosei.games.toybank.feature.game.setup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dosei.games.toybank.data.model.LeadPlayer
import com.dosei.games.toybank.data.model.Navigate
import com.dosei.games.toybank.data.model.None
import com.dosei.games.toybank.ui.widget.BackButton
import com.dosei.games.toybank.ui.widget.ColorChip
import com.dosei.games.toybank.ui.widget.RemovalBox

val PLAYERS_RANGE = 2 .. 6

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameSetupScreen(
    controller: NavHostController,
    viewModel: GameSetupViewModel,
) {
    var showAddPlayerBottomSheet by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()
    val actions = remember {
        GameSetupActions(
            onBack = { controller.popBackStack() },
            onAddPlayer = { showAddPlayerBottomSheet = true },
            onStart = { viewModel.onNewGameClick() },
            onRemove = { viewModel.removePlayer(it) }
        )
    }

    GameSetupContent(
        players = state.players,
        actions = actions
    )

    if (showAddPlayerBottomSheet) {
        val forbiddenNames by remember(state.players) {
            derivedStateOf { state.players.map { it.name } }
        }
        AddPlayerBottomSheet(
            availableColors = state.availableColors,
            forbiddenNames = forbiddenNames,
            onDismiss = { showAddPlayerBottomSheet = false },
            onConfirm = { name, color ->
                viewModel.createPlayer(name, color)
                showAddPlayerBottomSheet = false
            }
        )
    }

    val event by viewModel.events.collectAsState(None)
    LaunchedEffect(event) {
        when (event) {
            is Navigate<*> -> controller.navigate((event as Navigate<*>).route)
        }
    }
}

private data class GameSetupActions(
    val onBack: () -> Unit = {},
    val onStart: () -> Unit = {},
    val onAddPlayer: () -> Unit = {},
    val onRemove: (LeadPlayer) -> Unit = {},
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun GameSetupContent(
    players: List<LeadPlayer>,
    actions: GameSetupActions,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Game") },
                navigationIcon = { BackButton(actions.onBack) }
            )
        },
        bottomBar = {
            Surface {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    enabled = players.size in PLAYERS_RANGE,
                    onClick = actions.onStart
                ) {
                    Text("Start")
                }
            }
        },
        floatingActionButton = {
            if (players.size < PLAYERS_RANGE.last) {
                FloatingActionButton(
                    onClick = actions.onAddPlayer,
                    content = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Player"
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(Modifier.padding(innerPadding)) {
            if (players.isEmpty()) {
                item { EmptyMessage() }
            } else {
                items(players) { player ->
                    RemovalBox(player, actions.onRemove) {
                        PlayerRow(player)
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyMessage() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = "Add players to start a new game.",
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun PlayerRow(player: LeadPlayer) {
    Surface(
        shape = RectangleShape
    ) {
        ListItem(
            leadingContent = { ColorChip(Color(player.colorARGB)) },
            headlineContent = { Text(player.name) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        GameSetupContent(
            players = listOf(
                LeadPlayer("John", 0xFF6200EE.toInt()),
                LeadPlayer("Jane", 0xFF03DAC5.toInt()),
                LeadPlayer("Doe", 0xFFFF5722.toInt()),
                LeadPlayer("Alice", 0xFF4CAF50.toInt())
            ),
            actions = GameSetupActions()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEmpty() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        GameSetupContent(
            players = emptyList(),
            actions = GameSetupActions()
        )
    }
}
