package com.jgeniselli.banco.game.play

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

enum class PlayerOptionType {
    TRANSFER,
    DEBIT,
    CREDIT
}

@Composable
fun PlayerOptionsLoader(onSelectOption: (PlayerOptionType) -> Unit) {

}

@Composable
fun PlayerOptions(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
private fun PreviewPlayerOptions() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        PlayerOptions(
            modifier = Modifier
        )
    }
}