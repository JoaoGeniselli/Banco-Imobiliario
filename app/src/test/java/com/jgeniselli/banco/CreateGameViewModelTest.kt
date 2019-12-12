package com.jgeniselli.banco

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.jgeniselli.banco.game.common.domain.GameRepository
import com.jgeniselli.banco.game.common.domain.PlayerRepository
import com.jgeniselli.banco.game.create.CreateGameActivity
import com.jgeniselli.banco.game.create.CreateGameViewModel
import com.jgeniselli.banco.game.create.CreateGameViewState
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class CreateGameViewModelTest {

    @Before
    fun setUp() {

    }

    @Test
    fun `test activity lifecycle observation`() {
        val controller = Robolectric.buildActivity(CreateGameActivity::class.java)
            .create()
            .start()
        val activity = controller.get() as AppCompatActivity

        val playerRepository = mockk<PlayerRepository>(relaxed = true)
        val gameRepository = mockk<GameRepository>(relaxed = true)
        val viewModel = CreateGameViewModel(playerRepository, gameRepository)

        val observer = mockk<Observer<CreateGameViewState>>()

        activity.lifecycle.addObserver(viewModel)
        viewModel.observeViewState(activity, observer)

        controller.create()

        verify { observer.onChanged(CreateGameViewState.LoadingStart) }
        verify { playerRepository.findAll(any(), any()) }
    }
}