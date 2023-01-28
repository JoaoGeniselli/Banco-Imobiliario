package com.jgeniselli.banco.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.home.HomeViewModel
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeLoader(
    viewModel: HomeViewModel = koinViewModel(),
    onContinueGame: () -> Unit,
    onNewGame: () -> Unit
) {
    val isContinueGameEnabled = viewModel.isGameAvailable.collectAsState(initial = false)
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
    isContinueGameEnabled: Boolean,
    resolver: CurrencyValueResolver = get()
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.padding(16.dp)) {
        var value by remember { mutableStateOf(0.0) }
        NumberInput(
            onUpdate = { value = it },
            onDone = { /*TODO*/ },
            value = value,
            label = "Value"
        )


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