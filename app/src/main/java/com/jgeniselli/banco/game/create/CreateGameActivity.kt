package com.jgeniselli.banco.game.create

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jgeniselli.banco.R
import com.jgeniselli.banco.game.common.domain.InsufficientPlayersException
import com.jgeniselli.banco.game.common.view.color.ColorAdapter
import com.jgeniselli.banco.game.play.GameActivity
import kotlinx.android.synthetic.main.activity_create_game.*
import org.koin.android.viewmodel.ext.android.viewModel

class CreateGameActivity : AppCompatActivity() {

    private val viewModel by viewModel<CreateGameViewModel>()
    private val adapter = ColorAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_game)
        lifecycle.addObserver(viewModel)
        viewModel.observeViewState(this, Observer { applyViewState(it) })
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        recycler_view.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_game, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (R.id.done == item?.itemId) {
            createGame()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createGame() {
        val selectedPlayers = adapter.selectedRows
        try {
            viewModel.createGame(selectedPlayers)
        } catch (e: InsufficientPlayersException) {
            displayErrorToast(R.string.error_minimum_players_required)
        }
    }

    private fun applyViewState(state: CreateGameViewState) {
        when(state) {
            is CreateGameViewState.LoadingStart -> progress.visibility = View.VISIBLE
            is CreateGameViewState.LoadingStop -> progress.visibility = View.GONE
            is CreateGameViewState.ContentFound -> adapter.rows = state.rows
            is Error -> displayErrorToast(R.string.error_while_fetching_players)
            is CreateGameViewState.RedirectToGame -> redirectToGameScreen()
        }
    }

    private fun displayErrorToast(@StringRes messageId: Int) {
        Toast
            .makeText(this, messageId, Toast.LENGTH_LONG)
            .show()
    }

    private fun redirectToGameScreen() {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
