package com.jgeniselli.banco.game.create

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jgeniselli.banco.R
import com.jgeniselli.banco.game.common.view.player.selection.MultiplePlayerSelectionAdapter
import com.jgeniselli.banco.game.play.GameActivity
import kotlinx.android.synthetic.main.activity_create_game.*
import org.koin.android.viewmodel.ext.android.viewModel

class CreateGameActivity : AppCompatActivity() {

    private val viewModel by viewModel<CreateGameViewModel>()
    private val adapter = MultiplePlayerSelectionAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_game)
        lifecycle.addObserver(viewModel)
        viewModel.observeViewState(this, Observer { applyViewState(it) })

        recycler_view.layoutManager = LinearLayoutManager(this)
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
        val selectedPlayers = adapter.getSelectedPlayers()
        viewModel.createGame(selectedPlayers)
    }

    private fun applyViewState(state: CreateGameViewState) {
        when(state) {
            is CreateGameViewState.LoadingStart -> progress.visibility = View.VISIBLE
            is CreateGameViewState.LoadingStop -> progress.visibility = View.GONE
            is CreateGameViewState.ContentFound -> adapter.players = state.players
            is Error -> displayErrorToast()
            is CreateGameViewState.RedirectToGame -> redirectToGameScreen()
        }
    }

    private fun displayErrorToast() {
        Toast
            .makeText(this, R.string.error_while_fetching_players, Toast.LENGTH_LONG)
            .show()
    }

    private fun redirectToGameScreen() {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}
