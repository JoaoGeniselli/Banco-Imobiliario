package com.jgeniselli.banco.infra

import android.content.Context
import androidx.room.Room
import com.jgeniselli.banco.core.PlayerStorage
import com.jgeniselli.banco.infra.concurrency.AsyncRunners
import com.jgeniselli.banco.infra.concurrency.CoroutinesAsyncRunners
import com.jgeniselli.banco.infra.db.DBPlayerStorage
import com.jgeniselli.banco.infra.db.Database
import com.jgeniselli.banco.infra.memory.MemoryPlayerStorage

class Infrastructure internal constructor(
    val storage: PlayerStorage
) {
    companion object {
        fun create(def: InfraDefinition.() -> Unit): Infrastructure {
            val definition = InfraDefinition().apply(def)
            return definition.makeInfra()
        }
    }
}

class InfraDefinition {

    private var storage: PlayerStorage? = null

    private var concurrencyStrategyHasBeenSet = false

    fun coroutinesConcurrency() {
        AsyncRunners.inject(
            CoroutinesAsyncRunners.runnerIO,
            CoroutinesAsyncRunners.runnerMain,
            CoroutinesAsyncRunners.runnerComputation
        )
        concurrencyStrategyHasBeenSet = true
    }

    fun memoryStorage() {
        storage = MemoryPlayerStorage()
    }

    fun databaseStorage(context: Context) {
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "game_db"
        )
            .fallbackToDestructiveMigration()
            .build()
        storage = DBPlayerStorage(db.gameDao())
    }

    internal fun makeInfra() : Infrastructure {
        ensureConcurrencyIsValid()
        ensureStorageIsValid()
        return Infrastructure(storage!!)
    }

    private fun ensureConcurrencyIsValid() {
        if (!concurrencyStrategyHasBeenSet) {
            throw IllegalStateException("Concurrency strategy must be defined to create infra")
        }
    }

    private fun ensureStorageIsValid() {
        storage ?: throw IllegalStateException("A storage must be defined to infra creation")
    }
}