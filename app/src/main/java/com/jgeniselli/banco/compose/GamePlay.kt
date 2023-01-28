package com.jgeniselli.banco.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.R
import org.koin.androidx.compose.getViewModel

@Composable
fun GamePlayLoader(
    onTransfer: (selected: GameplayPlayer) -> Unit,
    viewModel: GamePlayViewModel = getViewModel()
) {
    val players by viewModel.players.collectAsState(initial = listOf())
    GamePlay(
        players = players,
        onClick = { position -> onTransfer(players[position]) }
    )
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
                GameplayPlayer(R.drawable.ic_baseline_pets_24, "John", "$ 300.00"),
                GameplayPlayer(R.drawable.ic_baseline_pets_24, "Emily", "$ 800.00")
            ),
            onClick = {}
        )
    }
}