package com.dosei.games.toybank.feature.game.setup

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dosei.games.toybank.ui.widget.ColorChip
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
fun AddPlayerBottomSheet(
    availableColors: List<Color>,
    onDismiss: () -> Unit,
    onConfirm: (name: String, color: Color) -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(availableColors.first()) }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            scope.launch { sheetState.hide() }.invokeOnCompletion { onDismiss() }
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        TextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            placeholder = { Text(text = "Player Name") }
        )

        Text(
            modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp),
            text = "Select the color"
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp),
            horizontalArrangement = spacedBy(8.dp)
        ) {
            availableColors.forEach { color ->
                Surface(
                    color = if (selectedColor == color) {
                        MaterialTheme.colorScheme.surfaceVariant
                    } else {
                        Color.Transparent
                    },
                    shape = MaterialTheme.shapes.small,
                    onClick = { selectedColor = color }
                ) {
                    ColorChip(modifier = Modifier.padding(6.dp), color = color)
                }
            }
        }

        Button(
            onClick = {
                scope.launch { sheetState.hide() }
                    .invokeOnCompletion { onConfirm(name, selectedColor) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 24.dp),
            enabled = name.isNotEmpty()
        ) {
            Text(text = "Add Player")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PreviewAddPlayerBottomSheet() {
    AddPlayerBottomSheet(
        sheetState = rememberStandardBottomSheetState(SheetValue.Expanded),
        onDismiss = {},
        onConfirm = { _, _ -> },
        availableColors = listOf(
            Color.Red,
            Color.Green,
            Color.Blue,
            Color.Yellow,
            Color.Magenta,
            Color.DarkGray
        )
    )
}
