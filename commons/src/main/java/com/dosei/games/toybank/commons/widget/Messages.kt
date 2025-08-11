package com.dosei.games.toybank.commons.widget

import android.content.Context
import com.dosei.games.toybank.commons.R
import com.dosei.games.toybank.core.data.model.UiError
import com.dosei.games.toybank.core.data.model.UiEvent
import com.dosei.games.toybank.core.data.model.error.ErrorCodes
import com.dosei.games.toybank.core.data.model.error.businessCode
import com.dosei.games.toybank.core.data.model.error.businessMessage

val messageByErrorCode = mapOf(
    ErrorCodes.INVALID_INITIAL_BALANCE to R.string.message_invalid_initial_balance,
    ErrorCodes.INVALID_AMOUNT to R.string.message_invalid_amount,
    ErrorCodes.INVALID_DESTINATION_PLAYER to R.string.message_invalid_destination_player,
)

fun Context.errorMessage(event: UiEvent) = errorMessage(cause = (event as UiError).cause)

fun Context.errorMessage(cause: Throwable): String =
    cause.businessMessage
        ?.takeIf { it.isNotBlank() }
        ?: errorMessage(code = cause.businessCode)

fun Context.errorMessage(code: String?): String {
    return getString(messageByErrorCode[code] ?: R.string.generic_error_message)
}
