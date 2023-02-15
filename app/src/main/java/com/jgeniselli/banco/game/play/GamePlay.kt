package com.jgeniselli.banco.game.play

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.R
import com.jgeniselli.banco.ui.component.TitleSubtitleRow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GamePlayLoader(
    onSelectOperation: (selected: GameplayPlayer, operation: PlayerOptionType) -> Unit,
    viewModel: GamePlayViewModel = getViewModel()
) {
    val players by viewModel.players.collectAsState(initial = listOf())
    var selectedPlayer by remember { mutableStateOf<GameplayPlayer?>(null) }
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    PlayerOptionsBottomSheet(
        state = bottomSheetState,
        onSelectOption = { operation ->
            selectedPlayer?.let { player -> onSelectOperation(player, operation) }
        },
    ) {
        GamePlay(
            players = players,
            onClick = { position ->
                selectedPlayer = players[position]
                scope.launch { bottomSheetState.show() }
            }
        )
    }
}

@Composable
fun GamePlay(
    modifier: Modifier = Modifier,
    players: List<GameplayPlayer>,
    onClick: (position: Int) -> Unit
) {
    LazyColumn(modifier) {
        itemsIndexed(players) { position, player ->
            TitleSubtitleRow(
                modifier = modifier.clickable { onClick(position) },
                icon = painterResource(id = player.icon),
                title = player.name,
                subtitle = player.formattedBalance
            )
            Divider(modifier = Modifier.padding(start = 56.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGamePlay() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        GamePlay(
            modifier = Modifier,
            players = listOf(
                GameplayPlayer(1, R.drawable.ic_baseline_pets_24, "John", "$ 300.00"),
                GameplayPlayer(1, R.drawable.ic_baseline_pets_24, "Emily", "$ 800.00")
            ),
            onClick = {}
        )
    }
}