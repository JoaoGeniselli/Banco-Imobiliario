package com.jgeniselli.banco.game.transaction

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jgeniselli.banco.R
import com.jgeniselli.banco.game.common.domain.Player
import com.jgeniselli.banco.game.common.view.player.selection.PlayerSelectionAdapter
import kotlinx.android.synthetic.main.activity_transaction.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TransactionActivity : AppCompatActivity() {

    private val viewModel by viewModel<TransactionViewModel> {
        parametersOf(playerIdExtra)
    }

    private val inputtedValue: Double
        get() = input_transaction_value.text.toString().toDouble()

    private val playerIdExtra: Int
        get() {
            if (intent?.extras?.containsKey(EXTRA_KEY_PLAYER_ID) == true) {
                return intent.getIntExtra(EXTRA_KEY_PLAYER_ID, 0)
            } else {
                throw IllegalStateException("Transaction activity must receive a player id")
            }
        }

    private val otherPlayersAdapter =
        PlayerSelectionAdapter { player ->
            applyTransactionIfInputIsOk { applyTransferTo(player) }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        lifecycle.addObserver(viewModel)
        viewModel.observeViewState(this, Observer { applyViewState(it) })

        button_debit.setOnClickListener { applyTransactionIfInputIsOk(viewModel::applyDebit) }
        button_credit.setOnClickListener { applyTransactionIfInputIsOk(viewModel::applyCredit) }

        other_players_recycler.layoutManager = LinearLayoutManager(this)
        other_players_recycler.adapter = otherPlayersAdapter
    }

    private fun applyTransactionIfInputIsOk(operation: (Double) -> Unit) {
        if (inputContainsNumbers()) {
            operation(inputtedValue)
        } else {
            input_transaction_value.error = getString(R.string.must_fill_input)
        }
    }

    private fun inputContainsNumbers(): Boolean {
        val input = input_transaction_value.text.toString()
        return input.matches(Regex(".*\\d.*"))
    }

    private fun applyViewState(it: TransactionViewState?) {
        when (it) {
            is TransactionViewState.Content -> {
                displayPlayerName(it.playerName)
                displayOtherPlayers(it.players)
                input_transaction_value.requestFocus()
            }
            is TransactionViewState.TransactionComplete -> finish()
        }
    }

    private fun displayPlayerName(name: String?) {
        text_player_name.text = getString(R.string.player_name_mask, name)
    }

    private fun displayOtherPlayers(players: List<Player>) {
        otherPlayersAdapter.players = players
    }

    private fun applyTransferTo(otherPlayer: Player) {
        viewModel.applyTransfer(inputtedValue, otherPlayer)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        private const val EXTRA_KEY_PLAYER_ID = "playerId"

        fun start(context: Context, selectedPlayerId: Int) {
            val intent = Intent(context, TransactionActivity::class.java)
            intent.putExtra(EXTRA_KEY_PLAYER_ID, selectedPlayerId)
            context.startActivity(intent)
        }
    }
}
