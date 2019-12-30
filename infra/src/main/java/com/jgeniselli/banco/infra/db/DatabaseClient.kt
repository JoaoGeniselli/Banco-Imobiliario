package com.jgeniselli.banco.infra.db

import android.content.Context
import androidx.room.Room

object DatabaseClient {

    lateinit var database: Database

    fun initDB(context: Context) {
        database = Room.databaseBuilder(context, Database::class.java, "db").build()
    }
}