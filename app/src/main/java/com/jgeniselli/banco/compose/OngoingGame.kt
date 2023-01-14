package com.jgeniselli.banco.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OngoingGame(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
private fun PreviewOngoingGame() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        OngoingGame(
            modifier = Modifier
        )
    }
}