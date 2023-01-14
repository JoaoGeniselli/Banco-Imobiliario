package com.jgeniselli.banco.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.R

@Composable
fun CircleIcon(
    modifier: Modifier = Modifier,
    color: Color,
    painter: Painter,
    contentDescription: String? = null
) {
    Image(
        modifier = modifier.size(40.dp)
            .background(color, CircleShape)
            .clip(CircleShape)
            .padding(8.dp),
        painter = painter,
        contentDescription = contentDescription
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewCircleIcon() {
    Surface(modifier = Modifier, color = Color.White) {
        CircleIcon(
            color = Color.Cyan,
            painter = painterResource(id = R.drawable.ic_baseline_pets_24)
        )
    }
}