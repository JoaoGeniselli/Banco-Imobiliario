package com.dosei.games.toybank.gameplay.presentation.play

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.dosei.games.toybank.commons.widget.rememberAnalytics
import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.core.navigation.AppRoutes
import com.dosei.games.toybank.core.toolbox.formatBlr
import com.dosei.games.toybank.gameplay.R
import com.dosei.games.toybank.gameplay.analytics.GameplayAnalytics
import com.dosei.games.toybank.gameplay.navigation.GameplayRoutes
import com.dosei.games.toybank.ui.theme.DeepOrange
import com.dosei.games.toybank.ui.theme.Green
import com.dosei.games.toybank.ui.widget.ColorChip
import com.dosei.games.toybank.core.R as CoreR

@Composable
internal fun GameplayScreen(
    controller: NavHostController,
    viewModel: GameplayViewModel,
) {
    val analytics = rememberAnalytics()
    val players by remember { viewModel.fetchPlayers() }.collectAsState(emptyList())
    val winner by remember { viewModel.observeWinner() }.collectAsState(null)

    val actions = remember {
        GameplayActions(
            onBack = { controller.popBackStack() },
            onClickHistory = {
                analytics.log(GameplayAnalytics.clickHistory)
                controller.navigate(AppRoutes.Game.History)
            },
            onClickPlayer = { player ->
                analytics.log(
                    event = GameplayAnalytics.clickPlayer,
                    properties = mapOf("id" to player.id.toString())
                )
                controller.navigate(AppRoutes.Transaction(player.id))
            }
        )
    }

    GameplayContent(
        players = players,
        actions = actions
    )

    LaunchedEffect(winner) {
        val snapshotWinner = winner
        if (snapshotWinner != null) {
            controller.navigate(
                GameplayRoutes.Winner(snapshotWinner.name, snapshotWinner.colorARGB)
            ) {
                popUpTo(GameplayRoutes.Gameplay) { inclusive = true }
            }
        }
    }

    LaunchedEffect(Unit) {
        analytics.log(GameplayAnalytics.display)
    }
}

private data class GameplayActions(
    val onBack: () -> Unit = {},
    val onClickPlayer: (Player) -> Unit = {},
    val onClickHistory: () -> Unit = {},
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun GameplayContent(
    players: List<Player>,
    actions: GameplayActions,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(CoreR.string.app_name)) },
                actions = {
                    IconButton(onClick = actions.onClickHistory) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = stringResource(
                                R.string.gameplay_action_access_history
                            )
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(players) { player ->
                ListItem(
                    modifier = Modifier.clickable { actions.onClickPlayer(player) },
                    leadingContent = { ColorChip(Color(player.colorARGB)) },
                    headlineContent = { Text(player.name) },
                    trailingContent = {
                        Text(
                            text = player.balanceInCents.formatBlr(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (player.balanceInCents > 0) {
                                Green
                            } else {
                                DeepOrange
                            }
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        GameplayContent(
            players = listOf(
                Player(
                    id = 1,
                    name = "Alice",
                    colorARGB = Color.Red.toArgb(),
                    balanceInCents = 1000_00
                ),
                Player(
                    id = 2,
                    name = "Bob",
                    colorARGB = Color.Green.toArgb(),
                    balanceInCents = -1500_00
                ),
                Player(
                    id = 3,
                    name = "Charlie",
                    colorARGB = Color.Blue.toArgb(),
                    balanceInCents = 2000_00
                ),
            ),
            actions = GameplayActions()
        )
    }
}
