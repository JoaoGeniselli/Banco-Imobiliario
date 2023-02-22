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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onContinueGame: () -> Unit,
    onNewGame: () -> Unit
) {
    val isContinueGameEnabled = viewModel.isContinueEnabled.collectAsState(initial = false)
    HomeContent(
        onNewGame = onNewGame,
        onContinueGame = onContinueGame,
        isContinueGameEnabled = isContinueGameEnabled.value
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    onNewGame: () -> Unit,
    onContinueGame: () -> Unit,
    isContinueGameEnabled: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.padding(16.dp)) {

        Spacer(modifier = Modifier.fillMaxHeight(.25f))

        Text(
            modifier = Modifier.padding(horizontal = 40.dp),
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onNewGame
        ) { Text(text = stringResource(R.string.home_option_new_game)) }

        Button(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            enabled = isContinueGameEnabled,
            onClick = onContinueGame
        ) { Text(text = stringResource(R.string.home_option_continue_game)) }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHome() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        HomeContent(
            modifier = Modifier,
            onContinueGame = {},
            onNewGame = {},
            isContinueGameEnabled = true
        )
    }
}