package com.jgeniselli.banco.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.R

@Composable
fun TitleSubtitleRow(
    modifier: Modifier = Modifier,
    icon: Painter,
    title: String,
    subtitle: String
) {
    Row(modifier.fillMaxWidth()) {
        CircleIcon(
            modifier = Modifier
                .size(56.dp)
                .padding(8.dp),
            color = Color.LightGray,
            painter = icon
        )
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp)
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
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
            icon = painterResource(id = R.drawable.ic_baseline_pets_24),
            title = "Title",
            subtitle = "Subtitle"
        )
    }
}