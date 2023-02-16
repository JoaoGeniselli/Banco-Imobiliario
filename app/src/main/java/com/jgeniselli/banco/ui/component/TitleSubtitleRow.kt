package com.jgeniselli.banco.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TitleSubtitleRow(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    iconColor: Color = Color.LightGray,
    subtitle: String
) {
    Row(modifier.fillMaxWidth()) {
        CircleIcon(
            modifier = Modifier
                .size(56.dp)
                .padding(8.dp)
                .align(CenterVertically),
            color = iconColor,
            imageVector = icon
        )
        Column(
            modifier = Modifier
                .align(CenterVertically)
                .padding(start = 8.dp)
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTitleSubtitleRow() {
    Surface(modifier = Modifier, color = Color.White) {
        TitleSubtitleRow(
            modifier = Modifier,
            icon = Icons.Default.Person,
            title = "Title",
            subtitle = "Subtitle"
        )
    }
}