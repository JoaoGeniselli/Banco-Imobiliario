package com.dosei.games.toybank.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dosei.games.toybank.core.navigation.AppRoutes

@Composable
fun HomeScreen(
    controller: NavHostController,
    viewModel: HomeViewModel,
) {
    val isContinueEnabled by viewModel.isContinueEnabled.collectAsState(false)
    val actions = remember {
        HomeActions(
            onBack = { controller.popBackStack() },
            onClickNewGame = { controller.navigate(AppRoutes.Game.Setup) },
            onClickContinue = { controller.navigate(AppRoutes.Game.Play) }
        )
    }
    HomeContent(
        isContinueEnabled = isContinueEnabled,
        actions = actions
    )
}

private data class HomeActions(
    val onBack: () -> Unit = {},
    val onClickNewGame: () -> Unit = {},
    val onClickContinue: () -> Unit = {},
)

@Composable
private fun HomeContent(
    isContinueEnabled: Boolean,
    actions: HomeActions,
) {
    Scaffold(
        contentWindowInsets = WindowInsets(left = 16.dp, right = 16.dp)
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Spacer(Modifier.weight(1f))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Toy Bank",
                style = MaterialTheme.typography.headlineLarge,
            )
            Spacer(Modifier.weight(1f))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = actions.onClickNewGame
            ) {
                Text("New Game")
            }

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                enabled = isContinueEnabled,
                onClick = actions.onClickContinue
            ) {
                Text("Continue")
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        HomeContent(
            isContinueEnabled = true,
            actions = HomeActions()
        )
    }
}
