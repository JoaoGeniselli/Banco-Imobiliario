package com.dosei.games.toybank.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosei.games.toybank.core.data.model.UiError
import com.dosei.games.toybank.core.data.model.UiEvent
import com.dosei.games.toybank.core.data.model.error.businessMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class StatefulViewModel<T : Any>(initialState: T) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _events = Channel<UiEvent>()
    val events = _events.receiveAsFlow()

    protected suspend fun sendEvent(event: UiEvent) = _events.send(event)

    protected fun updateState(block: (T) -> T) {
        _state.value = block(_state.value)
    }

    fun request(
        onError: suspend (Throwable) -> Unit = { sendEvent(UiError(it)) },
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
        block: suspend () -> Unit,
    ) {
        viewModelScope.launch(coroutineContext) {
            runCatching { block() }.onFailure { onError(it) }
        }
    }
}