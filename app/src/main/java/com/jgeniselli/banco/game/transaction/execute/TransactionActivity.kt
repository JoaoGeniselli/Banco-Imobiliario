package com.jgeniselli.banco.game.transaction.execute

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jgeniselli.banco.R
import com.jgeniselli.banco.game.common.view.player.selection.PlayerSelectionAdapter
import com.jgeniselli.banco.game.common.view.player.selection.TitleAndColor
import kotlinx.android.synthetic.main.activity_transaction.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TransactionActivity : AppCompatActivity() {

    private val viewModel by viewModel<TransactionViewModel> {
        parametersOf(playerIdExtra)
    }

    private val inputtedValue: Double
        get() = input_transaction_value.text.toString().toDouble()

    private val playerIdExtra: Long
        get() {
            if (intent?.extras?.containsKey(EXTRA_KEY_PLAYER_ID) == true) {
                return intent.getLongExtra(EXTRA_KEY_PLAYER_ID, 0L)
            } else {
                throw IllegalStateException("Transaction activity must receive a player id")
            }
        }

    private val otherPlayersAdapter =
        PlayerSelectionAdapter { selectedPosition ->
            applyTransactionIfInputIsOk { applyTransferTo(selectedPosition) }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        lifecycle.addObserver(viewModel)
        viewModel.viewState.observe(this, Observer { applyViewState(it) })

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
                displayOtherPlayers(it.otherPlayerRows)
                input_transaction_value.requestFocus()
            }
            is TransactionViewState.TransactionComplete -> finish()
        }
    }

    private fun displayPlayerName(name: String?) {
        text_player_name.text = getString(R.string.player_name_mask, name)
    }

    private fun displayOtherPlayers(players: List<TitleAndColor>) {
        otherPlayersAdapter.rows = players
    }

    private fun applyTransferTo(playerPosition: Int) {
        viewModel.applyTransfer(inputtedValue, playerPosition)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        private const val EXTRA_KEY_PLAYER_ID = "playerId"

        fun start(context: Context, selectedPlayerId: Long) {
            val intent = Intent(context, TransactionActivity::class.java)
            intent.putExtra(EXTRA_KEY_PLAYER_ID, selectedPlayerId)
            context.startActivity(intent)
        }
    }
}
