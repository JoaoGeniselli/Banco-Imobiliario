package com.dosei.games.toybank.feature.game.play.transaction.amount

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dosei.games.toybank.ui.widget.CurrencyTextField

@Composable
fun TransactionAmountScreen(
    controller: NavHostController
) {
    val amount by rememberSaveable { mutableIntStateOf(0) }
    TransactionAmountContent(
        actions = TransactionAmountActions(
            onBack = { controller.popBackStack() }
        ),
        amount = amount,
    )
}

private data class TransactionAmountActions(
    val onBack: () -> Unit = {},
    val onUpdateAmount: (Int) -> Unit = {},
    val onConfirm: () -> Unit = {},
)

@Composable
private fun TransactionAmountContent(
    amount: Int,
    modifier: Modifier = Modifier,
    actions: TransactionAmountActions,
) {
    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        CurrencyTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = amount,
            onUpdateValue = actions.onUpdateAmount,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )

        Spacer(Modifier.weight(1f))

        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = actions.onConfirm
        ) {
            Text("Confirm")
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        TransactionAmountContent(
            amount = 0,
            actions = TransactionAmountActions()
        )
    }
}
