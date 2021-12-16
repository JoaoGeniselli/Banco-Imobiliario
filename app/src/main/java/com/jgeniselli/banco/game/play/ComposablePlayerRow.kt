package com.jgeniselli.banco.game.play

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.R

@Composable
fun ComposablePlayerRow(
    modifier: Modifier = Modifier,
    name: String,
    balance: String,
    onClickCredit: () -> Unit,
    onClickDebit: () -> Unit,
    onClickTransfer: () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f),
                text = name
            )
            Text(
                text = balance
            )
        }
        Row(modifier = Modifier.align(CenterHorizontally)) {
            @Composable
            fun Icon(res: Int, description: String, onClick: () -> Unit) {
                Image(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(onClick = onClick),
                    colorFilter = ColorFilter.tint(Color.Black),
                    painter = painterResource(id = res),
                    contentDescription = description
                )
            }
            Icon(R.drawable.ic_baseline_add_24, "Credit", onClickCredit)
            Icon(R.drawable.ic_baseline_remove_24, "Debit", onClickDebit)
            Icon(R.drawable.ic_baseline_arrow_forward_24, "Transfer", onClickTransfer)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlayerRow() {
    ComposablePlayerRow(
        name = "Johnny",
        balance = "R$ 25.000",
        onClickCredit = {},
        onClickDebit = {},
        onClickTransfer = {}
    )
}