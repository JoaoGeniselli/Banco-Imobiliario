package com.dosei.games.toybank.feature.game.play.transaction.select

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.dosei.games.toybank.ui.widget.BackButton

@Composable
fun SelectTransactionScreen(
    controller: NavHostController
) {
    SelectTransactionContent(
        actions = SelectTransactionActions(
            onBack = { controller.popBackStack() }
        )
    )
}

private data class SelectTransactionActions(
    val onBack: () -> Unit = {},
    val onSelectDeposit: () -> Unit = {},
    val onSelectWithdraw: () -> Unit = {},
    val onSelectTransfer: () -> Unit = {},
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SelectTransactionContent(
    actions: SelectTransactionActions,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Toy Bank") },
                navigationIcon = {
                    BackButton(onClick = actions.onBack)
                },
            )
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            ListItem(
                modifier = Modifier.clickable(onClick = actions.onSelectDeposit),
                headlineContent = { Text(text = "Deposit") }
            )
            ListItem(
                modifier = Modifier.clickable(onClick = actions.onSelectWithdraw),
                headlineContent = { Text(text = "Withdraw") }
            )
            ListItem(
                modifier = Modifier.clickable(onClick = actions.onSelectTransfer),
                headlineContent = { Text(text = "Transfer") }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        SelectTransactionContent(
            actions = SelectTransactionActions()
        )
    }
}
