package com.dosei.games.toybank.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosei.games.toybank.core.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class HistoryViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    val history = repository.history
        .stateIn(viewModelScope, Lazily, emptyList())
}