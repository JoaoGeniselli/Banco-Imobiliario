package com.jgeniselli.banco.game.play

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jgeniselli.banco.R
import com.jgeniselli.banco.game.common.view.player.summary.PlayerSummaryAdapter
import com.jgeniselli.banco.game.transaction.TransactionActivity
import kotlinx.android.synthetic.main.activity_game.*
import org.koin.android.viewmodel.ext.android.viewModel

class GameActivity : AppCompatActivity() {

    private val viewModel by viewModel<GameViewModel>()
    private val adapter = PlayerSummaryAdapter(
        onPositionClickListener = { position -> viewModel.onPlayerSelected(position) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        setupViewModel()
        setupRecyclerView()
    }

    private fun setupViewModel() {
        lifecycle.addObserver(viewModel)
        viewModel.viewState.observe(this, Observer { applyViewState(it) })
    }

    private fun setupRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this@GameActivity)
        recycler_view.addItemDecoration(
            DividerItemDecoration(this@GameActivity, RecyclerView.VERTICAL)
        )
        recycler_view.adapter = adapter
    }

    private fun applyViewState(state: GameViewState) {
        when (state) {
            is GameViewState.PlayersFound -> adapter.players = state.players
            is GameViewState.Error -> displayErrorAlert(state.error)
            is GameViewState.RedirectToTransaction -> redirectToTransaction(state.playerId)
        }
    }

    private fun displayErrorAlert(error: String?) {
        val okListener = DialogInterface.OnClickListener { _, _ ->
            finish()
        }
        AlertDialog.Builder(this)
            .setTitle(R.string.error)
            .setMessage(error)
            .setNeutralButton(android.R.string.ok, okListener)
            .create()
            .show()
    }

    private fun redirectToTransaction(playerId: Long) {
        TransactionActivity.start(this, playerId)
    }
}
