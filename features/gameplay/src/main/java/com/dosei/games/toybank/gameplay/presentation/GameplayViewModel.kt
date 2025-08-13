package com.dosei.games.toybank.gameplay.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosei.games.toybank.core.data.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class GameplayViewModel @Inject constructor(
    private val repository: PlayerRepository
) : ViewModel() {

    fun fetchPlayers() = repository.players
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}