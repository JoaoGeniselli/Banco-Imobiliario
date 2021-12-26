package com.jgeniselli.banco.game.play

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jgeniselli.banco.game.play.ui.theme.BancoImobiliarioTheme

data class PlayerModel(
    val id: Int,
    val name: String,
    var balance: String
)

@Composable
fun ComposablePlayerList(
    modifier: Modifier = Modifier,
    players: List<PlayerModel>,
    onClickCredit: (position: Int) -> Unit,
    onClickDebit: (position: Int) -> Unit,
    onClickTransfer: (position: Int) -> Unit,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        itemsIndexed(players) { index, item ->
            ComposablePlayerRow(
                name = item.name,
                balance = item.balance,
                onClickCredit = { onClickCredit(index) },
                onClickDebit = { onClickDebit(index) },
                onClickTransfer = { onClickTransfer(index) }
            )
            Divider()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BancoImobiliarioTheme {
        ComposablePlayerList(
            players = listOf(
                PlayerModel(1, "Johnny", "R$ 25.000"),
                PlayerModel(2, "Emily", "R$ 30.000")
            ),
            onClickCredit = {},
            onClickDebit = {},
            onClickTransfer = {}
        )
    }
}