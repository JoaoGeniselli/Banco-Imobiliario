package com.dosei.games.toybank.commons.widget.receipt

import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.dosei.games.toybank.core.toolbox.formatBlr

@Composable
fun ReceiptAttribute(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    valueEmphasis: FontWeight = FontWeight.Normal
) {
    ListItem(
        modifier = modifier,
        overlineContent = { Text(title) },
        headlineContent = {
            Text(
                text = value,
                fontWeight = valueEmphasis
            )
        }
    )
}

@Preview
@Composable
private fun PreviewReceiptAmount() {
    ReceiptAttribute(
        modifier = Modifier,
        title = "Amount",
        value = 100_00.formatBlr()
    )
}
