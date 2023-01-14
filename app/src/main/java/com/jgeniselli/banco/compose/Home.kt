package com.jgeniselli.banco.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Home(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.fillMaxHeight(.25f))

        Text(text = "Banker App", style = MaterialTheme.typography.h3)

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) { Text(text = "New Game") }

        Button(
            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
            enabled = false,
            onClick = { /*TODO*/ }
        ) { Text(text = "Continue Game") }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHome() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Home(
            modifier = Modifier
        )
    }
}