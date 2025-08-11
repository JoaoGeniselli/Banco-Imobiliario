package com.dosei.games.toybank.commons.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.dosei.games.toybank.core.data.model.NavigateTo
import com.dosei.games.toybank.core.data.model.UiEvent

fun NavHostController.navigateTo(
    event: UiEvent,
    navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}
) {
    (event as? NavigateTo)?.let { navEvent ->
        navigateTo(navEvent, navOptionsBuilder)
    }
}

fun NavHostController.navigateTo(
    event: NavigateTo,
    navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}
) {
    when (event) {
        is NavigateTo.StringRoute -> navigate(event.route, navOptionsBuilder)
        is NavigateTo.GenericRoute<*> -> navigate(event.route, navOptionsBuilder)
    }
}