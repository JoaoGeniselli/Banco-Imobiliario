package com.jgeniselli.banco.ui.component

import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import kotlin.math.abs

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShortcutChip(value: Double, onShortcut: (Double) -> Unit) {
    Chip(
        onClick = { onShortcut(value) },
        leadingIcon = {
            Icon(
                imageVector = if (value > 0.0) Icons.Default.Add else Icons.Default.Remove,
                contentDescription = null
            )
        },
        content = { Text(text = abs(value).toCurrency()) }
    )
}