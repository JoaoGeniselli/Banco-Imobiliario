package com.dosei.games.toybank.gameplay.presentation.winner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dosei.games.toybank.core.navigation.AppRoutes
import com.dosei.games.toybank.gameplay.R
import com.dosei.games.toybank.ui.theme.DeepOrange
import com.dosei.games.toybank.core.R as CoreR

@Composable
internal fun WinnerScreen(
    controller: NavHostController,
    winnerName: String,
    winnerColor: Int,
) {
    WinnerContent(
        winnerName = winnerName,
        winnerColor = winnerColor,
        actions = WinnerActions(
            onBack = { controller.popBackStack(AppRoutes.Home, false) },
        )
    )
}

private data class WinnerActions(
    val onBack: () -> Unit = {},
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WinnerContent(
    winnerName: String,
    winnerColor: Int,
    actions: WinnerActions,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(CoreR.string.app_name)) }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))

            Icon(
                modifier = Modifier.size(64.dp),
                imageVector = Icons.Outlined.WorkspacePremium,
                contentDescription = stringResource(R.string.gameplay_winner_badge_description),
                tint = Color(winnerColor)
            )
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = stringResource(R.string.gameplay_game_over),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                modifier = Modifier.padding(top = 40.dp),
                text = stringResource(R.string.gameplay_winner_label),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = winnerName,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.weight(1f))

            Button(
                modifier = Modifier.padding(bottom = 24.dp),
                onClick = actions.onBack
            ) {
                Text(stringResource(R.string.gameplay_action_back_to_home))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        WinnerContent(
            actions = WinnerActions(),
            winnerName = "John",
            winnerColor = DeepOrange.toArgb()
        )
    }
}
