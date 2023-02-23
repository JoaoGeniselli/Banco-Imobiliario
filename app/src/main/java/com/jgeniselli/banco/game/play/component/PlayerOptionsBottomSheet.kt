package com.jgeniselli.banco.game.play.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.R
import com.jgeniselli.banco.core.entity.OperationType
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
            PlayerOptionsContent(onSelectOption = onSelectOption)
        },
        content = content
    )
}

@Composable
fun PlayerOptionsContent(modifier: Modifier = Modifier, onSelectOption: (OperationType) -> Unit) {
    Column(modifier) {
        IconLabelRow(
            modifier = Modifier.clickable { onSelectOption(OperationType.CREDIT) },
            icon = Icons.Default.Add,
            label = stringResource(R.string.credit_operation_label)
        )
        Divider(Modifier.padding(start = 56.dp))
        IconLabelRow(
            modifier = Modifier.clickable { onSelectOption(OperationType.DEBIT) },
            icon = Icons.Default.Remove,
            label = stringResource(R.string.debit_operation_label)
        )
        Divider(Modifier.padding(start = 56.dp))
        IconLabelRow(
            modifier = Modifier.clickable { onSelectOption(OperationType.TRANSFER) },
            icon = Icons.Default.SwapHoriz,
            label = stringResource(R.string.transfer_operation_label)
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewPlayerOptions() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        PlayerOptionsContent(
            modifier = Modifier,
            onSelectOption = {}
        )
    }
}