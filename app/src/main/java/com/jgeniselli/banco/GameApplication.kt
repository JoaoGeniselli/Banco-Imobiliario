package com.jgeniselli.banco

import android.app.Application
import com.jgeniselli.banco.infra.database.InfraModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GameApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        InfraModule.init(this)
        startKoin {
            androidContext(this@GameApplication)
            modules(DependencyInjection.mainModule)
        }
    }
}