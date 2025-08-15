package com.dosei.games.toybank

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ToyBankApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}