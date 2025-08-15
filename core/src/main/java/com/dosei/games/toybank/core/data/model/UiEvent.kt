package com.dosei.games.toybank.core.data.model

import android.os.Bundle

interface UiEvent

data object None : UiEvent

data object Close : UiEvent

sealed interface NavigateTo : UiEvent {

    data class StringRoute(
        val route: String,
        val options: Bundle? = null,
    ) : NavigateTo

    data class GenericRoute<T : Any>(
        val route: T,
        val options: Bundle? = null,
    ) : NavigateTo

    companion object Companion {

        operator fun invoke(
            route: String,
            options: Bundle? = null
        ): NavigateTo = StringRoute(route, options)

        operator fun <T : Any> invoke(
            route: T,
            options: Bundle? = null
        ): NavigateTo = GenericRoute(route, options)
    }
}

data class UiError(
    val cause: Throwable
) : UiEvent