package com.dosei.games.toybank.feature.game.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosei.games.toybank.data.repository.GameplayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GameplayViewModel @Inject constructor(
    private val repository: GameplayRepository
) : ViewModel() {

    val players = repository.players
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}