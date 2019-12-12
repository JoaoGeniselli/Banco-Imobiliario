package com.jgeniselli.banco.landing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.jgeniselli.banco.R
import com.jgeniselli.banco.game.create.CreateGameActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
        viewModel.observeEnableContinueEvent(
            this,
            Observer {
                button_continue.isEnabled = true
                button_continue.setOnClickListener { redirectToGamePlay() }
            }
        )
        setContentView(R.layout.activity_main)
        button_new_game.setOnClickListener {
            redirectToGameCreation()
        }
    }

    private fun redirectToGameCreation() {
        val intent = Intent(this, CreateGameActivity::class.java)
        startActivity(intent)
    }

    private fun redirectToGamePlay() {

    }
}
