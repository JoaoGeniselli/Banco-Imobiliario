package com.jgeniselli.banco.game.play

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jgeniselli.banco.R
import com.jgeniselli.banco.game.common.domain.Player
import com.jgeniselli.banco.game.common.view.player.summary.PlayerSummaryAdapter
import com.jgeniselli.banco.game.transaction.TransactionActivity
import kotlinx.android.synthetic.main.activity_game.*
import org.koin.android.viewmodel.ext.android.viewModel

class GameActivity : AppCompatActivity() {

    private val viewModel by viewModel<GameViewModel>()
    private val adapter = PlayerSummaryAdapter(
        onPlayerClickListener = { selectedPlayer -> redirectToTransaction(selectedPlayer) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        lifecycle.addObserver(viewModel)
        viewModel.observeViewState(this, Observer { applyViewState(it) })
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
    }

    private fun applyViewState(state: GameViewState) {
        when (state) {
            is GameViewState.PlayersFound -> adapter.players = state.players
            is GameViewState.Error -> displayErrorAlert()
        }
    }

    private fun displayErrorAlert() {
        val okListener = DialogInterface.OnClickListener { _, _ ->
            finish()
        }
        AlertDialog.Builder(this)
            .setTitle(R.string.error)
            .setMessage(R.string.error_while_starting_game)
            .setNeutralButton(android.R.string.ok, okListener)
            .create()
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_play, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (R.id.history == item?.itemId) {
            redirectToTransactionHistory()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun redirectToTransactionHistory() {
        // TODO IMPLEMENT
    }

    private fun redirectToTransaction(selectedPlayer: Player) {
        TransactionActivity.start(this, selectedPlayer.id)
    }
}
