package com.jgeniselli.banco.game.play

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.jgeniselli.banco.R

sealed class DialogDirector {

    abstract fun construct(builder: AlertDialog.Builder)

    class Error(
        private val onOk: DialogInterface.OnClickListener
    ) : DialogDirector() {

        override fun construct(builder: AlertDialog.Builder) {
            with(builder) {
                setTitle(R.string.error)
                setMessage(R.string.error_while_starting_game)
                setNeutralButton(android.R.string.ok, onOk)
            }
        }
    }

    class ConfirmReset(
        private val onOk: DialogInterface.OnClickListener,
        private val onCancel: DialogInterface.OnClickListener
    ) : DialogDirector() {

        override fun construct(builder: AlertDialog.Builder) {
            with(builder) {
                setTitle(R.string.reset_game)
                setMessage(R.string.message_reset_game_confirmation)
                setPositiveButton(android.R.string.ok, onOk)
                setNegativeButton(android.R.string.cancel, onCancel)
            }
        }

    }

}