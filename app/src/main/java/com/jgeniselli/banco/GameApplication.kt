package com.jgeniselli.banco

import android.app.Application
import com.jgeniselli.banco.game.common.domain.db.DatabaseClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GameApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseClient.initDB(this)
        startKoin {
            androidContext(this@GameApplication)
            modules(KoinModule.mainModule)
        }
    }
}