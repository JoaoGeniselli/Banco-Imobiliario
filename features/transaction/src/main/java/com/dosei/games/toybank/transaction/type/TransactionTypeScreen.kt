package com.dosei.games.toybank.transaction.type

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.dosei.games.toybank.commons.navigation.navigateTo
import com.dosei.games.toybank.core.data.model.NavigateTo
import com.dosei.games.toybank.core.data.model.None
import com.dosei.games.toybank.core.data.model.TransactionType
import com.dosei.games.toybank.transaction.TransactionViewModel
import com.dosei.games.toybank.ui.widget.BackButton

@Composable
fun TransactionTypeScreen(
    controller: NavHostController,
    viewModel: TransactionViewModel,
) {
    TransactionTypeContent(
        actions = TransactionTypeActions(
            onBack = { controller.popBackStack() }
        )
    )

    val event by viewModel.events.collectAsState(None)
    LaunchedEffect(event) {
        when (event) {
            is NavigateTo -> controller.navigateTo(event)
        }
    }
}

private data class TransactionTypeActions(
    val onBack: () -> Unit = {},
    val onSelectType: (TransactionType) -> Unit = {},
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TransactionTypeContent(
    actions: TransactionTypeActions,
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
                modifier = Modifier.clickable { actions.onSelectType(TransactionType.DEPOSIT) },
                headlineContent = { Text(text = "Deposit") }
            )
            ListItem(
                modifier = Modifier.clickable { actions.onSelectType(TransactionType.WITHDRAW) },
                headlineContent = { Text(text = "Withdraw") }
            )
            ListItem(
                modifier = Modifier.clickable { actions.onSelectType(TransactionType.TRANSFER) },
                headlineContent = { Text(text = "Transfer") }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        TransactionTypeContent(
            actions = TransactionTypeActions()
        )
    }
}
