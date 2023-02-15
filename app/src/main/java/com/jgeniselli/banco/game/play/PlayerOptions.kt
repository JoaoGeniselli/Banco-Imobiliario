package com.jgeniselli.banco.game.play

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.R
import com.jgeniselli.banco.ui.component.IconLabelRow

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun PlayerOptionsBottomSheet(
    state: ModalBottomSheetState,
    onSelectOption: (OperationType) -> Unit,
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
fun PlayerOptions(modifier: Modifier = Modifier, onSelectOption: (OperationType) -> Unit) {
    Column(modifier) {
        IconLabelRow(
            modifier = Modifier.clickable { onSelectOption(OperationType.CREDIT) },
            icon = painterResource(R.drawable.ic_baseline_add_24),
            label = stringResource(R.string.credit_operation_label)
        )
        Divider(Modifier.padding(start = 56.dp))
        IconLabelRow(
            modifier = Modifier.clickable { onSelectOption(OperationType.DEBIT) },
            icon = painterResource(R.drawable.ic_baseline_remove_24),
            label = stringResource(R.string.debit_operation_label)
        )
        Divider(Modifier.padding(start = 56.dp))
        IconLabelRow(
            modifier = Modifier.clickable { onSelectOption(OperationType.TRANSFER) },
            icon = painterResource(R.drawable.ic_baseline_swap_horiz_24),
            label = stringResource(R.string.transfer_operation_label)
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