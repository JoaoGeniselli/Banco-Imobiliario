package com.dosei.games.toybank.feature.game.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dosei.games.toybank.data.model.LeadPlayer
import com.dosei.games.toybank.ui.widget.BackButton

@Composable
fun GameSetupScreen(
    controller: NavHostController
) {
//    GameSetupContent(
//        actions = GameSetupActions(
//            onBack = { controller.popBackStack() }
//        )
//    )
}

private data class GameSetupActions(
    val onBack: () -> Unit = {},
    val onAddPlayer: () -> Unit = {},
    val onStart: () -> Unit = {},
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
                    enabled = players.isNotEmpty(),
                    onClick = actions.onStart
                ) {
                    Text("Start")
                }
            }
        },
        floatingActionButton = {
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
    ) { innerPadding ->
        LazyColumn(Modifier.padding(innerPadding)) {

            if (players.isEmpty()) {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = "Add players to start a new game.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            items(players) { player ->
                ListItem(
                    leadingContent = {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color(player.colorARGB), CircleShape)
                        )
                    },
                    headlineContent = { Text(player.name) }
                )

            }
        }
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
