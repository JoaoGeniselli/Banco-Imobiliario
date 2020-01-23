package com.jgeniselli.banco.infra.concurrency

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal object CoroutinesAsyncRunners {
    val runnerIO : AsyncRunner = { GlobalScope.launch(context = Dispatchers.IO) { it() } }
    val runnerMain : AsyncRunner = { GlobalScope.launch(context = Dispatchers.Main) { it() } }
    val runnerComputation: AsyncRunner = { GlobalScope.launch(context = Dispatchers.Default) { it() } }
}