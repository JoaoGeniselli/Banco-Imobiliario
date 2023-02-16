package com.jgeniselli.banco.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class PlayerSummary(val name: String, val color: Color, val isHighlight: Boolean = false)

@Composable
fun PlayerList(
    modifier: Modifier = Modifier,
    players: List<PlayerSummary>,
    onClick: (position: Int) -> Unit = {}
) {
    LazyColumn(modifier) {
        itemsIndexed(players) { index, player ->
            IconLabelRow(
                modifier = Modifier
                    .background(
                        if (player.isHighlight) Color.LightGray else MaterialTheme.colors.background
                    )
                    .clickable { onClick(index) },
                icon = Icons.Default.Person,
                label = player.name,
                color = player.color
            )
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
                PlayerSummary("Player 1", Color.Red),
                PlayerSummary("Player 2", Color.Green),
                PlayerSummary("Player 3", Color.Blue, true),
                PlayerSummary("Player 4", Color.LightGray),
            )
        )
    }
}