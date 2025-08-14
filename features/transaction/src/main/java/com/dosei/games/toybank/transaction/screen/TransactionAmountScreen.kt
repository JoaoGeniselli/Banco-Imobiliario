package com.dosei.games.toybank.transaction.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dosei.games.toybank.commons.navigation.navigateTo
import com.dosei.games.toybank.commons.widget.CurrencyTextField
import com.dosei.games.toybank.commons.widget.showErrorFrom
import com.dosei.games.toybank.core.data.model.Close
import com.dosei.games.toybank.core.data.model.NavigateTo
import com.dosei.games.toybank.core.data.model.None
import com.dosei.games.toybank.core.data.model.UiError
import com.dosei.games.toybank.core.navigation.AppRoutes
import com.dosei.games.toybank.transaction.R
import com.dosei.games.toybank.transaction.TransactionViewModel
import com.dosei.games.toybank.ui.widget.BackButton

@Composable
internal fun TransactionAmountScreen(
    controller: NavHostController,
    viewModel: TransactionViewModel,
) {
    var amount by rememberSaveable { mutableIntStateOf(0) }
    val isLoading by viewModel.isLoading.collectAsState(false)
    val actions = remember {
        TransactionAmountActions(
            onUpdateAmount = { amount = it },
            onConfirm = { viewModel.onConfirmAmount(amount) },
            onBack = { controller.popBackStack() }
        )
    }

    TransactionAmountContent(
        amount = amount,
        isLoading = isLoading,
        actions = actions,
    )

    val context = LocalContext.current
    val event by viewModel.events.collectAsState(None)
    LaunchedEffect(event) {
        when (event) {
            is NavigateTo -> controller.navigateTo(event)
            is Close -> controller.popBackStack(AppRoutes.Transaction::class, true)
            is UiError -> context.showErrorFrom(event)
        }
    }
}

private data class TransactionAmountActions(
    val onUpdateAmount: (Int) -> Unit = {},
    val onConfirm: () -> Unit = {},
    val onBack: () -> Unit = {},
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TransactionAmountContent(
    amount: Int,
    isLoading: Boolean,
    actions: TransactionAmountActions,
) {
    val focusRequester = remember { FocusRequester() }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.transaction_feature_title)) },
                navigationIcon = { BackButton(actions.onBack) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Text(stringResource(R.string.transaction_amount_label))
            Spacer(Modifier.height(16.dp))

            CurrencyTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                enabled = !isLoading,
                value = amount,
                onUpdateValue = actions.onUpdateAmount,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )

            Spacer(Modifier.weight(1f))

            Button(
                modifier = Modifier.align(Alignment.End),
                enabled = !isLoading,
                onClick = actions.onConfirm
            ) {
                Text(stringResource(R.string.transaction_action_confirm))
            }

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    TransactionAmountContent(
        amount = 200_00,
        actions = TransactionAmountActions(),
        isLoading = true
    )
}
