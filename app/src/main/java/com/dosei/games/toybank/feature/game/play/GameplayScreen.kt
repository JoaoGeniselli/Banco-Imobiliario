package com.dosei.games.toybank.feature.game.play

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.toolbox.formatBlr
import com.dosei.games.toybank.ui.widget.BackButton
import com.dosei.games.toybank.ui.widget.ColorChip

@Composable
fun GameplayScreen(
    controller: NavHostController,
    viewModel: GameplayViewModel,
) {
    val players by viewModel.players.collectAsState()
    val actions = remember {
        GameplayActions(
            onBack = { controller.popBackStack() }
        )
    }
    GameplayContent(
        players = players,
        actions = actions
    )
}

private data class GameplayActions(
    val onBack: () -> Unit = {},
    val onMore: () -> Unit = {},
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
                title = { Text("Toy Bank") },
                actions = {
                    IconButton(onClick = actions.onMore) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options"
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
                    leadingContent = { ColorChip(Color(player.colorARGB)) },
                    headlineContent = { Text(player.name) },
                    trailingContent = { Text(player.balanceInCents.formatBlr()) }
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
                    balanceInCents = 1500_00
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
