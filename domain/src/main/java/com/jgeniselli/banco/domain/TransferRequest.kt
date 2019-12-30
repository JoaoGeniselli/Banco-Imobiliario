package com.jgeniselli.banco.domain

data class TransferRequest(
    val sourcePlayer: Player,
    val destinationPlayer: Player,
    val value: Double
)