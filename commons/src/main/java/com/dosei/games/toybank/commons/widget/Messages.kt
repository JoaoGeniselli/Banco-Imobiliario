package com.dosei.games.toybank.commons.widget

import android.content.Context
import android.widget.Toast
import com.dosei.games.toybank.core.R
import com.dosei.games.toybank.core.data.model.UiError
import com.dosei.games.toybank.core.data.model.UiEvent
import com.dosei.games.toybank.core.data.model.error.ErrorCode
import com.dosei.games.toybank.core.data.model.error.businessCode
import com.dosei.games.toybank.core.data.model.error.businessMessage

fun Context.showErrorFrom(event: UiEvent) {
    (event as? UiError)?.let {
        Toast.makeText(
            this@showErrorFrom,
            errorMessage(event),
            Toast.LENGTH_LONG
        ).show()
    }
}

fun Context.errorMessage(event: UiError) = errorMessage(cause = event.cause)

fun Context.errorMessage(cause: Throwable): String =
    cause.businessMessage
        ?.takeIf { it.isNotBlank() }
        ?: errorMessage(code = cause.businessCode)

fun Context.errorMessage(code: ErrorCode?): String =
    getString(code?.resourceMessage ?: R.string.generic_error_message)
