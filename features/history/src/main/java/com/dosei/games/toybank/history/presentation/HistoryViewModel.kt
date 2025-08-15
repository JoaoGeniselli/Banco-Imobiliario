package com.dosei.games.toybank.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosei.games.toybank.history.data.model.HistoryEntry
import com.dosei.games.toybank.history.data.usecase.LoadHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class HistoryViewModel @Inject constructor(
    private val loadHistory: LoadHistory
) : ViewModel() {

    fun fetchHistory(): StateFlow<List<HistoryEntry>> = loadHistory()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}