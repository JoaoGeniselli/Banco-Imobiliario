package com.jgeniselli.banco.core

import com.jgeniselli.banco.core.repository.GameRepository
import com.jgeniselli.banco.core.repository.GameRepositoryImpl
import com.jgeniselli.banco.core.repository.GameStorage

object CoreModule {

    fun gameRepository(storage: GameStorage): GameRepository = GameRepositoryImpl(storage)
}