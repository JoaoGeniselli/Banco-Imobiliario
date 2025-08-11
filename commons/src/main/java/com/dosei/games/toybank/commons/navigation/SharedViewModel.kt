package com.dosei.games.toybank.commons.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    controller: NavHostController
): T {
    val graphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        controller.getBackStackEntry(graphRoute)
    }
    return hiltViewModel(viewModelStoreOwner = parentEntry)
}