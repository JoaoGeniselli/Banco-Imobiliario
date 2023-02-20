package com.jgeniselli.banco.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeLoader(
    viewModel: HomeViewModel = koinViewModel(),
    onContinueGame: () -> Unit,
    onNewGame: () -> Unit
) {
    val isContinueGameEnabled = viewModel.isContinueEnabled.collectAsState(initial = false)
    Home(
        onNewGame = onNewGame,
        onContinueGame = onContinueGame,
        isContinueGameEnabled = isContinueGameEnabled.value
    )
}

@Composable
fun Home(
    modifier: Modifier = Modifier,
    onNewGame: () -> Unit,
    onContinueGame: () -> Unit,
    isContinueGameEnabled: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.padding(16.dp)) {

        Spacer(modifier = Modifier.fillMaxHeight(.25f))

        Text(text = "Banker App", style = MaterialTheme.typography.h3)

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onNewGame
        ) { Text(text = "New Game") }

        Button(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            enabled = isContinueGameEnabled,
            onClick = onContinueGame
        ) { Text(text = "Continue Game") }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHome() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Home(
            modifier = Modifier,
            onContinueGame = {},
            onNewGame = {},
            isContinueGameEnabled = true
        )
    }
}