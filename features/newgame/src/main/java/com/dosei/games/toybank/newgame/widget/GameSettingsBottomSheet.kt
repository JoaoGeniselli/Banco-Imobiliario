package com.dosei.games.toybank.newgame.widget

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dosei.games.toybank.commons.widget.CurrencyTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameSettingsBottomSheet(
    initialBalance: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    val scope = rememberCoroutineScope()
    var updatedInitialBalance by remember(initialBalance) { mutableIntStateOf(initialBalance) }
    val isError by remember(updatedInitialBalance) {
        derivedStateOf { updatedInitialBalance == 0 }
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            scope.launch { sheetState.hide() }.invokeOnCompletion { onDismiss() }
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        CurrencyTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            value = updatedInitialBalance,
            onUpdateValue = { updatedInitialBalance = it },
            label = { Text(text = "Player's initial balance") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words
            ),
            isError = isError,
            supportingText = if (isError) {
                { Text(text = "Balance must be greater than zero.") }
            } else null
        )

        Button(
            onClick = {
                scope.launch { sheetState.hide() }
                    .invokeOnCompletion { onConfirm(updatedInitialBalance) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 24.dp),
            enabled = !isError
        ) {
            Text(text = "Save")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PreviewGameSettingsBottomSheet() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        GameSettingsBottomSheet(
            initialBalance = 3000_00,
            onDismiss = {},
            onConfirm = {},
            sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Expanded)
        )
    }
}
