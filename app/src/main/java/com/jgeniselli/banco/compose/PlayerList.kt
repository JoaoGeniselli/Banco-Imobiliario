package com.jgeniselli.banco.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.R

data class PlayerSummary(val name: String, @DrawableRes val icon: Int)

@Composable
fun PlayerList(modifier: Modifier = Modifier, players: List<PlayerSummary>) {
    LazyColumn(modifier) {
        items(players) { player ->
            IconLabelRow(icon = painterResource(player.icon), label = player.name)
            Divider(modifier = Modifier.padding(start = 56.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPlayerList() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        PlayerList(
            modifier = Modifier,
            players = listOf(
                PlayerSummary("Player 1", R.drawable.ic_baseline_pets_24),
                PlayerSummary("Player 2", R.drawable.ic_baseline_pets_24),
                PlayerSummary("Player 3", R.drawable.ic_baseline_pets_24),
                PlayerSummary("Player 4", R.drawable.ic_baseline_pets_24),
            )
        )
    }
}