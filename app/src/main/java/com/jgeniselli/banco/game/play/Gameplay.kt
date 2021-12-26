package com.jgeniselli.banco.game.play

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import com.jgeniselli.banco.R

@Composable
fun GameplayLoader(
    viewModel: GameplayViewModel,
    onClickOperationLog: () -> Unit
) {
    val players = viewModel.players.observeAsState(listOf())
    Gameplay(
        players = players.value,
        onRequestCredit = viewModel::onCreditRequested,
        onRequestDebit = viewModel::onDebitRequested,
        onRequestTransfer = viewModel::onTransferRequested,
        onClickOperationLog = onClickOperationLog
    )
}

@Composable
fun Gameplay(
    players: List<PlayerModel>,
    onRequestCredit: (id: Int, value: String) -> Unit,
    onRequestDebit: (id: Int, value: String) -> Unit,
    onRequestTransfer: (senderId: Int, receiverId: Int, value: String) -> Unit,
    onClickOperationLog: () -> Unit
) {
    val showCreditModal = remember { mutableStateOf(false) }
    val showDebitModal = remember { mutableStateOf(false) }
    val showTransferModal = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TopAppBar(
                title = { Text("Gameplay") },
                actions = {
                    IconButton(onClick = onClickOperationLog) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_history_24dp),
                            contentDescription = "Operation Log"
                        )
                    }
                }
            )
            ComposablePlayerList(
                players = players,
                onClickCredit = { showCreditModal.value = true },
                onClickDebit = { showDebitModal.value = true },
                onClickTransfer = { showTransferModal.value = true }
            )
        }

        if (showCreditModal.value) {
            Dialog(onDismissRequest = { showCreditModal.value = false }) {
                val value = ""
                ComposableTextDialogContent(
                    message = "Credit",
                    value = "",
                    label = "",
                    onValueChange = {}
                ) {

                }
            }
        }
    }
}