package com.jgeniselli.banco.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.R

@Composable
fun IconLabelRow(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    color: Color = MaterialTheme.colors.secondary,
) {
    Row(modifier.fillMaxWidth()) {
        CircleIcon(
            modifier = Modifier
                .size(56.dp)
                .padding(8.dp),
            color = color,
            imageVector = icon
        )
        Text(
            modifier = Modifier
                .align(CenterVertically)
                .padding(start = 16.dp)
                .padding(vertical = 8.dp),
            text = label,
            style = MaterialTheme.typography.subtitle1
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewIconLabelRow() {
    Surface(color = Color.White) {
        IconLabelRow(
            icon = Icons.Default.Person,
            label = "Label"
        )
    }
}