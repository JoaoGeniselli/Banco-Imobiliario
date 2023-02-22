package com.jgeniselli.banco.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircleIcon(
    modifier: Modifier = Modifier,
    color: Color,
    imageVector: ImageVector,
    contentDescription: String? = null
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCircleIcon() {
    Surface(modifier = Modifier, color = Color.White) {
        CircleIcon(
            modifier = Modifier
                .size(72.dp)
                .padding(16.dp),
            color = Color.Cyan,
            imageVector = Icons.Default.Person
        )
    }
}