package com.dosei.games.toybank.transaction.screen.beneficiary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dosei.games.toybank.commons.navigation.navigateTo
import com.dosei.games.toybank.core.data.model.NavigateTo
import com.dosei.games.toybank.core.data.model.None
import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.transaction.TransactionViewModel
import com.dosei.games.toybank.ui.widget.BackButton
import kotlinx.coroutines.flow.map

@Composable
fun TransactionBeneficiaryScreen(
    controller: NavHostController,
    parentViewModel: TransactionViewModel,
    viewModel: TransactionBeneficiaryViewModel,
) {
    val playerId by remember { parentViewModel.state.map { it.playerId } }.collectAsState(0)
    val players by remember(playerId) {
        viewModel.loadPlayers(playerId)
    }.collectAsState(emptyList())

    TransactionBeneficiaryContent(
        players = players,
        actions = TransactionBeneficiaryActions(
            onBack = { controller.popBackStack() },
            onSelectPlayer = { beneficiary ->
                parentViewModel.onBeneficiarySelected(beneficiary.id)
            }
        )
    )

    val event by parentViewModel.events.collectAsState(None)
    LaunchedEffect(event) {
        when (event) {
            is NavigateTo -> controller.navigateTo(event)
        }
    }
}

private data class TransactionBeneficiaryActions(
    val onBack: () -> Unit = {},
    val onSelectPlayer: (Player) -> Unit = {},
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionBeneficiaryContent(
    players: List<Player>,
    actions: TransactionBeneficiaryActions,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Toy Bank") },
                navigationIcon = { BackButton(onClick = actions.onBack) },
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 24.dp)
        ) {
            item {
                Text(modifier = Modifier.padding(16.dp), text = "Select the destination player:")
            }
            items(players) { player ->
                ListItem(
                    modifier = Modifier.clickable { actions.onSelectPlayer(player) },
                    headlineContent = { Text(player.name) },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                            contentDescription = "Navigate to Deposit"
                        )
                    }
                )
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        TransactionBeneficiaryContent(
            players = listOf(
                Player(name = "John"),
                Player(name = "Mary"),
                Player(name = "Claude"),
            ),
            actions = TransactionBeneficiaryActions()
        )
    }
}
