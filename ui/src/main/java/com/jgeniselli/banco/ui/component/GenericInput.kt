package com.jgeniselli.banco.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.ui.R

@Composable
fun GenericInput(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    actionEnabled: Boolean,
    actionLabel: String = stringResource(id = R.string.generic_input_label),
    onAction: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(style = MaterialTheme.typography.h4, text = title)
        Text(style = MaterialTheme.typography.subtitle1, text = subtitle)

        content(this)

        Spacer(modifier = Modifier.weight(1f))

        Button(
            enabled = actionEnabled,
            modifier = Modifier.fillMaxWidth(),
            onClick = onAction
        ) {
            Text(text = actionLabel)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGenericInput() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        GenericInput(
            modifier = Modifier,
            title = "Title",
            subtitle = "Subtitle",
            actionEnabled = true,
            actionLabel = "Done",
            onAction = {}
        ) {
            Text(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(16.dp),
                text = "Generic content"
            )
        }
    }
}