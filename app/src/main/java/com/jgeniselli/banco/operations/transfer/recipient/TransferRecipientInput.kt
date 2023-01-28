package com.jgeniselli.banco.operations.transfer.recipient

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.R
import com.jgeniselli.banco.ui.component.GenericInput
import com.jgeniselli.banco.ui.component.PlayerList
import com.jgeniselli.banco.ui.component.PlayerSummary

@Composable
fun TransferRecipientInputLoader() {

}

@Composable
fun TransferRecipientInput(modifier: Modifier = Modifier, players: List<PlayerSummary>) {
    GenericInput(
        modifier = modifier,
        title = "Transaction Recipient",
        subtitle = "Select the player",
        actionEnabled = true,
        onAction = { /*TODO*/ }
    ) {
        PlayerList(modifier = Modifier.padding(top= 16.dp), players = players)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTransferRecipientInput() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        TransferRecipientInput(
            modifier = Modifier,
            players = listOf(
                PlayerSummary("Player 1", R.drawable.ic_baseline_pets_24),
                PlayerSummary("Player 2", R.drawable.ic_baseline_pets_24),
                PlayerSummary("Player 3", R.drawable.ic_baseline_pets_24),
            )
        )
    }
}