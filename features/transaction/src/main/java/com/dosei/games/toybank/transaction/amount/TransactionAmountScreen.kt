package com.dosei.games.toybank.transaction.amount

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dosei.games.toybank.commons.widget.CurrencyTextField
import com.dosei.games.toybank.transaction.TransactionViewModel

@Composable
fun TransactionAmountScreen(
    controller: NavHostController,
    viewModel: TransactionViewModel,
) {
    var amount by rememberSaveable { mutableIntStateOf(0) }
    TransactionAmountContent(
        amount = amount,
        onUpdateAmount = { amount = it },
        onConfirm = {
            viewModel.onConfirmAmount(amount)
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TransactionAmountContent(
    amount: Int,
    onUpdateAmount: (Int) -> Unit,
    onConfirm: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Transferência") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            CurrencyTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = amount,
                onUpdateValue = onUpdateAmount,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )

            Spacer(Modifier.weight(1f))

            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = onConfirm
            ) {
                Text("Confirm")
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
        onUpdateAmount = { },
        onConfirm = { }
    )
}
