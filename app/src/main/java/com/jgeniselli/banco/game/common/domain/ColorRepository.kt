package com.jgeniselli.banco.game.common.domain

interface ColorRepository {
    fun findAll(onSuccess: (List<Color>) -> Unit, onError: () -> Unit)
}
