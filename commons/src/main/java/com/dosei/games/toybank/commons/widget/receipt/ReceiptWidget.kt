package com.dosei.games.toybank.commons.widget.receipt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.dosei.games.toybank.core.toolbox.formatBlr

@Composable
fun ReceiptWidget(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(modifier) {
        Surface {
            Column(Modifier) {
                content()
            }
        }
    }
}

@Preview
@Composable
private fun PreviewReceiptWidget() {
    ReceiptWidget(modifier = Modifier) {
        ReceiptHeader("This is a Receipt!")
        ReceiptAttribute(
            title = "Amount",
            value = 250_35.formatBlr(),
            valueEmphasis = FontWeight.Bold
        )
        ReceiptAttribute(
            title = "Date",
            value = "09/08/2025 10:00",
        )
        ReceiptAttribute(
            title = "Player",
            value = "John",
        )
    }
}
