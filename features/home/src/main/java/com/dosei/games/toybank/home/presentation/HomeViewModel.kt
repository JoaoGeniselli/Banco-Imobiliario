package com.dosei.games.toybank.home.presentation

import androidx.lifecycle.ViewModel
import com.dosei.games.toybank.home.data.usecase.HasOngoingGame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val hasOngoingGame: HasOngoingGame
): ViewModel() {

    fun isContinueEnabled() = flow { emit(hasOngoingGame()) }
}