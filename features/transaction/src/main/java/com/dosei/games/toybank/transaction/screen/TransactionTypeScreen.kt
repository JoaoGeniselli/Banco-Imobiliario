package com.dosei.games.toybank.transaction.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dosei.games.toybank.commons.navigation.navigateTo
import com.dosei.games.toybank.core.data.model.NavigateTo
import com.dosei.games.toybank.core.data.model.None
import com.dosei.games.toybank.core.data.model.TransactionType
import com.dosei.games.toybank.transaction.R
import com.dosei.games.toybank.transaction.TransactionViewModel
import com.dosei.games.toybank.ui.widget.BackButton
import com.dosei.games.toybank.core.R as CoreR

@Composable
internal fun TransactionTypeScreen(
    controller: NavHostController,
    viewModel: TransactionViewModel,
) {
    TransactionTypeContent(
        actions = TransactionTypeActions(
            onBack = { controller.popBackStack() },
            onSelectType = { type -> viewModel.onSelectType(type) }
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
                title = { Text(stringResource(CoreR.string.app_name)) },
                navigationIcon = { BackButton(onClick = actions.onBack) },
            )
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.transaction_select_type_label)
            )
            Option(
                onClick =  { actions.onSelectType(TransactionType.DEPOSIT) },
                title = stringResource(R.string.transaction_type_deposit)
            )
            HorizontalDivider()
            Option(
                onClick = { actions.onSelectType(TransactionType.WITHDRAW) },
                title = stringResource(R.string.transaction_type_withdraw)
            )
            HorizontalDivider()
            Option(
                onClick = { actions.onSelectType(TransactionType.TRANSFER) },
                title = stringResource(R.string.transaction_type_transfer),
            )
            HorizontalDivider()
        }
    }
}

@Composable
private fun Option(onClick: () -> Unit, title: String) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineContent = { Text(text = title) },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = stringResource(R.string.transaction_navigate_to, title)
            )
        }
    )
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
