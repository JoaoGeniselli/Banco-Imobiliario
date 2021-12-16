package com.jgeniselli.banco.game.play

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.livedata.observeAsState
import com.jgeniselli.banco.game.create.CreateGameLoader
import com.jgeniselli.banco.game.create.CreateGameViewModel
import com.jgeniselli.banco.game.play.ui.theme.BancoImobiliarioTheme
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import org.koin.core.get

class ComposeGameActivity : ComponentActivity(), KoinComponent {

    private val viewModel: StartupViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BancoImobiliarioTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val initCreation = viewModel.initGameCreation.observeAsState()

                    if (initCreation.value != null) {
                        val viewModel = get<CreateGameViewModel>()
                        CreateGameLoader(viewModel = viewModel)
                    }
                    viewModel.start()
                }
            }
        }
    }
}