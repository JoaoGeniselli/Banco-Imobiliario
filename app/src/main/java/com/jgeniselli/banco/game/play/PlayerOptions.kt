package com.jgeniselli.banco.game.play

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.R
import com.jgeniselli.banco.ui.component.IconLabelRow
import kotlinx.coroutines.launch

enum class PlayerOptionType {
    TRANSFER,
    DEBIT,
    CREDIT
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun PlayerOptionsBottomSheet(
    state: ModalBottomSheetState,
    onSelectOption: (PlayerOptionType) -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            PlayerOptions(onSelectOption = onSelectOption)
        },
        content = content
    )
}

@Composable
fun PlayerOptions(modifier: Modifier = Modifier, onSelectOption: (PlayerOptionType) -> Unit) {
    Column(modifier) {
        IconLabelRow(
            modifier = Modifier.clickable { onSelectOption(PlayerOptionType.CREDIT) },
            icon = painterResource(R.drawable.ic_baseline_add_24),
            label = "Credit"
        )
        Divider(Modifier.padding(start = 56.dp))
        IconLabelRow(
            modifier = Modifier.clickable { onSelectOption(PlayerOptionType.DEBIT) },
            icon = painterResource(R.drawable.ic_baseline_remove_24),
            label = "Debit"
        )
        Divider(Modifier.padding(start = 56.dp))
        IconLabelRow(
            modifier = Modifier.clickable { onSelectOption(PlayerOptionType.TRANSFER) },
            icon = painterResource(R.drawable.ic_baseline_swap_horiz_24),
            label = "Transfer"
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewPlayerOptions() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        PlayerOptions(
            modifier = Modifier,
            onSelectOption = {}
        )
    }
}