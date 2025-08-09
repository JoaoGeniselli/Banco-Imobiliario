package com.dosei.games.toybank.ui.widget

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.dosei.games.toybank.toolbox.formatBlr

@Composable
fun CurrencyTextField(
    value: Int,
    onUpdateValue: (Int) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions()
) {
    val formattedValue by remember(value) {
        derivedStateOf { value.formatBlr() }
    }
    TextField(
        modifier = modifier,
        value = TextFieldValue(
            text = formattedValue,
            selection = TextRange(formattedValue.length)
        ),
        keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Number),
        placeholder = { Text("R\$ 0,00") },
        onValueChange = { newValue ->
            val rawValueInCents = newValue.text.filter { it.isDigit() }
            val intValueInCents = runCatching { rawValueInCents.toInt() }.getOrDefault(0)
            onUpdateValue(intValueInCents)
        }
    )
}

@Preview
@Composable
private fun PreviewCurrencyTextField() {
    CurrencyTextField(
        modifier = Modifier,
        value = 10_00,
        onUpdateValue = {}
    )
}
