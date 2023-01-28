package com.jgeniselli.banco.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.R

@Composable
fun TransferConclusionLoader(
    onOk: () -> Unit
) {

}

@Composable
fun TransferConclusion(
    modifier: Modifier = Modifier,
    source: String,
    recipient: String,
    value: String,
    dateTime: String
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CircleIcon(
            modifier = Modifier
                .size(72.dp)
                .align(CenterHorizontally),
            painter = painterResource(id = R.drawable.ic_baseline_attach_money_24),
            color = Color.LightGray
        )
        Text(
            modifier = Modifier.align(CenterHorizontally),
            style = MaterialTheme.typography.h4,
            text = "Transaction Finished"
        )
        Text(
            modifier = Modifier.align(CenterHorizontally),
            style = MaterialTheme.typography.subtitle1,
            text = dateTime
        )


        Text(modifier = Modifier.padding(top = 16.dp), text = "From:", style = MaterialTheme.typography.caption)
        Text(text = source, style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold))

        Text(modifier = Modifier.padding(top = 16.dp), text = "To:", style = MaterialTheme.typography.caption)
        Text(text = recipient, style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold))

        Text(modifier = Modifier.padding(top = 16.dp), text = "Value: ", style = MaterialTheme.typography.caption)
        Text(text = value, style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold))

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        ) {
            Text(text = "OK")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTransferConclusion() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        TransferConclusion(
            modifier = Modifier,
            source = "Player 1",
            recipient = "Player 2",
            value = "$ 300,00",
            dateTime = "14/01/2022 13:00:00"
        )
    }
}