package com.jgeniselli.banco

import android.app.Application
import com.jgeniselli.banco.core.GameAPI
import com.jgeniselli.banco.infra.concurrency.AsyncRunners
import com.jgeniselli.banco.infra.concurrency.CoroutinesAsyncRunners
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GameApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AsyncRunners.inject(
            CoroutinesAsyncRunners.runnerIO,
            CoroutinesAsyncRunners.runnerMain,
            CoroutinesAsyncRunners.runnerComputation
        )
        startKoin {
            androidContext(this@GameApplication)
            modules(KoinModule.mainModule)
        }
        startGame()
    }

    private fun startGame() {
        getKoin().get<GameAPI>().startGameIfNeeded()
    }
}