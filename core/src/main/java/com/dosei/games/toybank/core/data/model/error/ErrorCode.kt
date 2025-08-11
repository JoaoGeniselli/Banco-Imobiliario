package com.dosei.games.toybank.core.data.model.error

import androidx.annotation.StringRes
import com.dosei.games.toybank.core.R

enum class ErrorCode(@StringRes val resourceMessage: Int?) {
    INVALID_AMOUNT(R.string.message_invalid_amount),
    INVALID_INITIAL_BALANCE(R.string.message_invalid_initial_balance),
    INVALID_PLAYER_AMOUNT(R.string.message_invalid_player_amount),
    INVALID_PLAYER(R.string.message_invalid_player),
    INVALID_DESTINATION_PLAYER(R.string.message_invalid_destination_player),
}