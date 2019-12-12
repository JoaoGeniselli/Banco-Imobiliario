package com.jgeniselli.banco.game.transaction

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jgeniselli.banco.R
import com.jgeniselli.banco.game.common.domain.Player
import com.jgeniselli.banco.game.common.view.player.selection.SinglePlayerSelectionAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TransactionActivity : AppCompatActivity() {

    private val viewModel by viewModel<TransactionViewModel> {
        parametersOf(playerIdExtra)
    }

    private val inputtedValue: Double
        get() = input_value.text.toString().toDouble()

    private val playerIdExtra: Int
        get() {
            if (intent?.extras?.containsKey(EXTRA_KEY_PLAYER_ID) == true) {
                return intent.getIntExtra(EXTRA_KEY_PLAYER_ID, 0)
            } else {
                throw IllegalStateException("Transaction activity must receive a player id")
            }
        }

    private val otherPlayersAdapter =
        SinglePlayerSelectionAdapter { player ->
            applyTransferTo(player)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        lifecycle.addObserver(viewModel)
        viewModel.observeViewState(this, Observer {
            when (it) {
                is TransactionViewState.Loading -> {
                }
                is TransactionViewState.Content -> {
                    displayPlayerName(it.playerName)
                    displayOtherPlayers(it.players)
                    input_value.requestFocus()
                }
                is TransactionViewState.TransactionComplete -> finish()
            }
        })
        button_debit.setOnClickListener {
            viewModel.applyDebit(inputtedValue)
        }
        button_credit.setOnClickListener {
            viewModel.applyCredit(inputtedValue)
        }

        other_players_recycler.layoutManager = LinearLayoutManager(this)
        other_players_recycler.adapter = otherPlayersAdapter
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

    companion object {
        private const val EXTRA_KEY_PLAYER_ID = "playerId"

        fun start(context: Context, selectedPlayerId: Int) {
            val intent = Intent(context, TransactionActivity::class.java)
            intent.putExtra(EXTRA_KEY_PLAYER_ID, selectedPlayerId)
            context.startActivity(intent)
        }
    }
}
