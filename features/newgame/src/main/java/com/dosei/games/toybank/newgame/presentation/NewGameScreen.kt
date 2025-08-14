package com.dosei.games.toybank.newgame.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dosei.games.toybank.commons.navigation.navigateTo
import com.dosei.games.toybank.commons.widget.showErrorFrom
import com.dosei.games.toybank.core.data.model.NavigateTo
import com.dosei.games.toybank.core.data.model.None
import com.dosei.games.toybank.core.data.model.UiError
import com.dosei.games.toybank.core.navigation.AppRoutes
import com.dosei.games.toybank.newgame.R
import com.dosei.games.toybank.newgame.data.model.LeadPlayer
import com.dosei.games.toybank.newgame.data.usecase.PLAYERS_RANGE
import com.dosei.games.toybank.newgame.widget.AddPlayerBottomSheet
import com.dosei.games.toybank.newgame.widget.GameSettingsBottomSheet
import com.dosei.games.toybank.ui.widget.BackButton
import com.dosei.games.toybank.ui.widget.ColorChip
import com.dosei.games.toybank.ui.widget.RemovalBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NewGameScreen(
    controller: NavHostController,
    viewModel: NewGameViewModel,
) {
    var showAddPlayerBottomSheet by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState(false)
    val actions = remember {
        NewGameActions(
            onBack = { controller.popBackStack() },
            onAddPlayer = { showAddPlayerBottomSheet = true },
            onStart = { viewModel.onNewGameClick() },
            onRemove = { viewModel.removePlayer(it) },
            onClickSettings = { showSettings = true }
        )
    }

    NewGameContent(
        players = state.players,
        isLoading = isLoading,
        actions = actions
    )

    when {
        showAddPlayerBottomSheet -> {
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

        showSettings -> {
            GameSettingsBottomSheet(
                initialBalance = state.initialBalanceInCents,
                onDismiss = { showSettings = false },
                onConfirm = { viewModel.onUpdateInitialBalance(it); showSettings = false }
            )
        }
    }
    EventHandler(viewModel, controller)
}

private data class NewGameActions(
    val onBack: () -> Unit = {},
    val onStart: () -> Unit = {},
    val onAddPlayer: () -> Unit = {},
    val onRemove: (LeadPlayer) -> Unit = {},
    val onClickSettings: () -> Unit = {},
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NewGameContent(
    players: List<LeadPlayer>,
    actions: NewGameActions,
    isLoading: Boolean = false,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.new_game_feature_title)) },
                navigationIcon = {
                    BackButton(
                        enabled = !isLoading,
                        onClick = actions.onBack
                    )
                },
                actions = { SettingsButton(isLoading, actions) }
            )
        },
        bottomBar = {
            Surface {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    enabled = players.size in PLAYERS_RANGE && !isLoading,
                    onClick = actions.onStart
                ) {
                    Text(stringResource(R.string.new_game_action_start))
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
                    RemovalBox(onRemove = { actions.onRemove(player) }) {
                        PlayerRow(player)
                    }
                }
            }

            if (isLoading) {
                item { LoadingIndicatorRow() }
            }
        }
    }
}

@Composable
private fun SettingsButton(
    isLoading: Boolean,
    actions: NewGameActions
) {
    IconButton(
        enabled = !isLoading,
        onClick = actions.onClickSettings
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = stringResource(
                R.string.new_game_settings_description
            )
        )
    }
}

@Composable
private fun EventHandler(
    viewModel: NewGameViewModel,
    controller: NavHostController
) {
    val event by viewModel.events.collectAsState(None)
    val context = LocalContext.current
    LaunchedEffect(event) {
        when (event) {
            is NavigateTo -> controller.navigateTo(event) {
                popUpTo(AppRoutes.Game.New) { inclusive = true }
            }

            is UiError -> context.showErrorFrom(event)
        }
    }
}

@Composable
private fun LoadingIndicatorRow() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyMessage() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = stringResource(R.string.new_game_empty_message),
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
        NewGameContent(
            players = listOf(
                LeadPlayer("John", 0xFF6200EE.toInt()),
                LeadPlayer("Jane", 0xFF03DAC5.toInt()),
                LeadPlayer("Doe", 0xFFFF5722.toInt()),
                LeadPlayer("Alice", 0xFF4CAF50.toInt())
            ),
            actions = NewGameActions(),
            isLoading = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEmpty() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        NewGameContent(
            players = emptyList(),
            actions = NewGameActions()
        )
    }
}
