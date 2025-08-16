package com.dosei.games.toybank.analytics

internal fun buildEvent(name: String, vararg appends: String) =
    (listOf(name) + appends.filter { it.isNotEmpty() }).joinToString("_")