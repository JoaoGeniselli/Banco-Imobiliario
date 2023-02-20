package com.jgeniselli.banco.topbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.jgeniselli.banco.Routes

data class TopBarAction(
    val icon: ImageVector,
    val name: String,
    val onClick: () -> Unit
)

fun createHistoryTopBarAction(controller: NavController): TopBarAction {
    return TopBarAction(
        icon = Icons.Default.History,
        name = "History",
        onClick = { controller.navigate(Routes.HISTORY) }
    )
}

@Composable
fun TopBarActionButton(modifier: Modifier = Modifier, action: TopBarAction) {
    IconButton(modifier = modifier, onClick = action.onClick) {
        Icon(imageVector = action.icon, contentDescription = action.name)
    }
}