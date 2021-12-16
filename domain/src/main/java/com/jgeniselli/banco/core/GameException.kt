package com.jgeniselli.banco.core

class GameException(
    val code: ErrorCode
) : Throwable()