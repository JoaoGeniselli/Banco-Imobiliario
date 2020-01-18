package com.jgeniselli.banco.game.transaction.history

import androidx.lifecycle.*
import com.jgeniselli.banco.core.GameAPI
import com.jgeniselli.banco.core.StoredTransactionDto
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.NumberFormat

class TransactionHistoryViewModel(
    private val gameAPI: GameAPI,
    private val formatter: NumberFormat
) : ViewModel(), LifecycleObserver {

    companion object {
        private const val VALUE_COLOR_GREEN = "#4caf50"
        private const val VALUE_COLOR_RED = "#f44336"
    }

    private val historyEvent = MutableLiveData<List<HistoryRow>>()

    val historyLiveData: LiveData<List<HistoryRow>>
        get() = historyEvent

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        GlobalScope.launch {
            val history = gameAPI.getTransactionHistory()
            val rows = history.map { it.toRow() }
            historyEvent.postValue(rows)
        }
    }

    private fun StoredTransactionDto.toRow(): HistoryRow {
        val valueColor = if (value < 0.0) VALUE_COLOR_RED else VALUE_COLOR_GREEN
        return HistoryRow(formatter.format(value), playerColor, valueColor)
    }
}
