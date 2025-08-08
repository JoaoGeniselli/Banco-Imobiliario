package com.dosei.games.toybank.feature.home

import androidx.lifecycle.ViewModel
import com.dosei.games.toybank.data.model.UiEvent
import com.dosei.games.toybank.data.repository.GameSetupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: GameSetupRepository
): ViewModel() {

    val isContinueEnabled = flow {
        emit(repository.hasOngoingGame())
    }

    val _events = Channel<UiEvent>()
    val events = _events.receiveAsFlow()


}