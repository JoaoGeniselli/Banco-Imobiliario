package com.dosei.games.toybank.history.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable
fun HistoryScreen(
    controller: NavHostController
) {
    HistoryContent(
        actions = HistoryActions(
            onBack = { controller.popBackStack() }
        )
    )
}

private data class HistoryActions(
    val onBack: () -> Unit = {},
)

@Composable
private fun HistoryContent(
    actions: HistoryActions,
) {
    actions.onBack()

}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        HistoryContent(
            actions = HistoryActions()
        )
    }
}
