package com.jgeniselli.banco.compose

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class Player(val name: String, val balance: Double)

class GameRepository {

    val players: Flow<List<Player>> = flow {

    }
}