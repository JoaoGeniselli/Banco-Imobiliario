package com.jgeniselli.banco.operations.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.ui.component.ShortcutChip

const val CURRENCY_AMOUNT_HUNDRED = 100.0
const val CURRENCY_AMOUNT_THOUSAND = 1000.0

@Composable
fun ValueInputShortcuts(modifier: Modifier = Modifier, onShortcut: (Double) -> Unit) {
    Row(modifier.padding(top = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ShortcutChip(value = +CURRENCY_AMOUNT_HUNDRED, onShortcut = onShortcut)
        ShortcutChip(value = -CURRENCY_AMOUNT_HUNDRED, onShortcut = onShortcut)
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ShortcutChip(value = +CURRENCY_AMOUNT_THOUSAND, onShortcut = onShortcut)
        ShortcutChip(value = -CURRENCY_AMOUNT_THOUSAND, onShortcut = onShortcut)
    }
}