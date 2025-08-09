package com.dosei.games.toybank.commons.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AmountInputWidget(
    amount: Int,
    onUpdateAmount: (Int) -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
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

@Preview(showBackground = true)
@Composable
private fun PreviewAmountInputWidget() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        AmountInputWidget(
            modifier = Modifier,
            onUpdateAmount = {},
            onConfirm = {},
            amount = 250_00
        )
    }
}
