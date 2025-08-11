package com.dosei.games.toybank.feature.home

import androidx.lifecycle.ViewModel
import com.dosei.games.toybank.core.data.repository.GameSetupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: GameSetupRepository
): ViewModel() {

    val isContinueEnabled = flow {
        emit(repository.hasOngoingGame())
    }

}