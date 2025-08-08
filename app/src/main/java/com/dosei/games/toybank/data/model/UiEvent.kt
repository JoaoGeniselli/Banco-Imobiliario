package com.dosei.games.toybank.data.model

import android.os.Bundle

interface UiEvent

data object None : UiEvent

data class Navigate<T : Any>(
    val route: T,
    val options: Bundle? = null,
) : UiEvent

data class UiError(
    val cause: Throwable
) : UiEvent